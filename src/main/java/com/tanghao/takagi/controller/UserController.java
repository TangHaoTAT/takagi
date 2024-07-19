package com.tanghao.takagi.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.vo.CommonResult;
import com.tanghao.takagi.vo.UserInfoEditVo;
import com.tanghao.takagi.vo.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

/**
 * @description 用户Controller
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @Value("${file.upload-folder}")
    private String uploadFolder;

    @Value("${file.access-url-prefix}")
    private String accessUrlPrefix;

    @GetMapping("/getCurrentUserInfo")
    @Operation(summary ="获取当前用户信息")
    public CommonResult<UserInfoVo> getCurrentUserInfo() {
        return CommonResult.data(userInfoService.getCurrentUserInfo());
    }

    @PostMapping("/updateCurrentUserInfo")
    @Operation(summary ="更新当前用户信息")
    public CommonResult updateCurrentUserInfo(@RequestBody UserInfoEditVo userInfoEditVo) {
        String nickname = userInfoEditVo.getNickname();
        String introduce = userInfoEditVo.getIntroduce();
        if (StrUtil.isBlank(nickname)) {
            throw new RuntimeException("昵称不能为空");
        }
        userInfoService.updateCurrentUserInfo(nickname, introduce);
        return CommonResult.ok();
    }

    @SneakyThrows
    @Operation(summary ="上传头像")
    @PostMapping(value = "/uploadAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult uploadAvatar(@RequestPart MultipartFile file) {
        long fileSize = file.getSize();
        String fileType = FileTypeUtil.getType(file.getInputStream());
        String[] PIC_EXT = {"png", "jpg"};
        if (fileSize > 2 * 1024 * 1024 || !ArrayUtil.contains(PIC_EXT, fileType)) {
            throw new RuntimeException("图片需小于2MB，仅支持JPG、PNG格式");
        }
        // 按照avatar/2024/5/15/uuid.png格式存储至uploadFolder路径下
        Date date = DateUtil.date(System.currentTimeMillis());
        int year = DateUtil.year(date);
        int month = DateUtil.month(date) + 1;
        int day = DateUtil.dayOfMonth(date);
        String dirName = "avatar/" + year + "/" + month + "/" + day + "/";
        String fileName = IdUtil.simpleUUID() + "." + fileType;
        String dirPath = uploadFolder + dirName;
        if (!FileUtil.exist(dirPath)) {
            FileUtil.mkdir(dirPath);
        }
        String filePath = uploadFolder + dirName + fileName;
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.writeFromStream(file.getInputStream());
        String avatarUrl = accessUrlPrefix + dirName + fileName;
        userInfoService.updateCurrentUserAvatar(avatarUrl);
        return CommonResult.ok();
    }

    @Operation(summary ="更新当前用户密码")
    @PostMapping(value = "/setPassword")
    public CommonResult setPassword(String newPassword) {
        userInfoService.updateCurrentUserPassword(SecureUtil.md5(newPassword));
        return CommonResult.ok();
    }
}
