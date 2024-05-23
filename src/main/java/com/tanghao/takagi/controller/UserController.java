package com.tanghao.takagi.controller;

import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.vo.CommonResult;
import com.tanghao.takagi.vo.UserInfoEditVo;
import com.tanghao.takagi.vo.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getCurrentUserInfo")
    @Operation(summary ="获取当前用户信息")
    public CommonResult<UserInfoVo> getCurrentUserInfo() {
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo();
        return CommonResult.data(userInfoVo);
    }

    @PostMapping("/userInfoEdit")
    @Operation(summary ="编辑用户信息")
    public CommonResult userInfoEdit(@RequestBody UserInfoEditVo userInfoEditVo) {
        return CommonResult.ok();
    }

    /*@Operation(summary ="更新头像")
    @PostMapping(value = "/uploadAvatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult uploadAvatar(@RequestPart MultipartFile file) throws IOException {
        long fileSize = file.getSize();
        String fileType = FileTypeUtil.getType(file.getInputStream());
        String[] PIC_EXT = {"png", "jpg"};
        if (fileSize > 2 * 1024 * 1024 || !ArrayUtil.contains(PIC_EXT, fileType)) {
            throw new RuntimeException("图片需小于2MB，仅支持JPG、PNG格式");
        }
        // 如2024-05-15上传名为abc.png的图片，将按照/2024/5/15/uuid.png格式存储至uploadFolder路径下
//        Date date = DateUtil.date(System.currentTimeMillis());
//        int year = DateUtil.year(date);
//        int month = DateUtil.month(date) + 1;
//        int day = DateUtil.dayOfMonth(date);
//        String dirName = year + "/" + month + "/" + day + "/";
        String fileName = IdUtil.simpleUUID() + "." + fileType;
        String dirName = "avatar/";
        String dirPath = uploadFolder + dirName;
        if (!FileUtil.exist(dirPath)) {
            FileUtil.mkdir(dirPath);
        }
        String filePath = uploadFolder + dirName + fileName;
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.writeFromStream(file.getInputStream());
        String relativePath = dirName + fileName;
        userInfoService.updateUserAvatar(relativePath);
        return CommonResult.ok();
    }*/
}
