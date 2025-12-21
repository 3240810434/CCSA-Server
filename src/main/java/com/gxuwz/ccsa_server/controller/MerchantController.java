package com.gxuwz.ccsa_server.controller;

import com.gxuwz.ccsa_server.entity.Merchant;
import com.gxuwz.ccsa_server.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantRepository merchantRepository;

    // 这里的路径需要根据你的实际电脑路径修改，用于存放上传的图片
    // 也可以配置 Spring Boot 的静态资源映射
    private static final String UPLOAD_DIR = "D:/ccsa_uploads/";

    @GetMapping("/{id}")
    public Merchant getMerchant(@PathVariable int id) {
        return merchantRepository.findById(id).orElse(null);
    }

    // 提交资质审核接口
    @PostMapping("/submitQualification")
    public Merchant submitQualification(
            @RequestParam("id") int id,
            @RequestParam("front") MultipartFile front,
            @RequestParam("back") MultipartFile back,
            @RequestParam("license") MultipartFile license) {

        Merchant merchant = merchantRepository.findById(id).orElse(null);
        if (merchant == null) return null;

        try {
            // 保存文件并生成访问 URL (这里简化处理，实际需配置 WebMvcConfigurer 映射路径)
            String frontUrl = saveFile(front);
            String backUrl = saveFile(back);
            String licenseUrl = saveFile(license);

            merchant.setIdCardFrontUri(frontUrl);
            merchant.setIdCardBackUri(backUrl);
            merchant.setLicenseUri(licenseUrl);
            merchant.setQualificationStatus(1); // 状态变为 1 (审核中)

            return merchantRepository.save(merchant);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取待审核列表
    @GetMapping("/audit/pending")
    public List<Merchant> getPendingAudits(@RequestParam(value = "community", required = false) String community) {
        List<Merchant> allMerchants = merchantRepository.findAll();
        return allMerchants.stream()
                .filter(m -> m.getQualificationStatus() == 1) // 1 表示审核中
                .filter(m -> community == null || community.isEmpty() || m.getCommunity().equals(community))
                .collect(Collectors.toList());
    }

    // 获取某小区的商家列表
    @GetMapping("/list")
    public List<Merchant> getMerchantsByCommunity(@RequestParam("community") String community) {
        List<Merchant> all = merchantRepository.findAll();
        return all.stream()
                .filter(m -> m.getCommunity().equals(community))
                .collect(Collectors.toList());
    }

    // 管理员审核操作
    @PostMapping("/audit/action")
    public Merchant auditMerchant(@RequestParam("id") int id, @RequestParam("status") int status) {
        Merchant merchant = merchantRepository.findById(id).orElse(null);
        if (merchant != null) {
            merchant.setQualificationStatus(status);
            return merchantRepository.save(merchant);
        }
        return null;
    }

    // 删除/注销商家
    @DeleteMapping("/delete/{id}")
    public String deleteMerchant(@PathVariable int id) {
        merchantRepository.deleteById(id);
        return "success";
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(dir, fileName);
        file.transferTo(dest);

        // 返回访问路径，注意：Android端需要能访问到这个IP
        // 需在 Spring Boot 配置 addResourceHandlers 映射 /uploads/** 到本地目录
        return "http://192.168.13.251:8080/uploads/" + fileName;
    }
}