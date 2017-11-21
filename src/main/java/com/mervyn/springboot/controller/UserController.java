package com.mervyn.springboot.controller;

import com.mervyn.springboot.model.User;
import com.mervyn.springboot.util.ExcelUtils;
import com.mervyn.springboot.vo.BusinessStatus;
import com.mervyn.springboot.vo.Result;
import com.mervyn.springboot.vo.UserExporter;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by mengran.gao on 2017/11/17.
 */
@RestController
public class UserController {

    @ApiOperation(value = "导入用户信息", notes = "导入用户信息", httpMethod = "POST")
    @RequestMapping(value = "/importUser", method = RequestMethod.POST)
    public Result<Object> importUser(MultipartFile file) throws IOException, ParseException {
        if (null == file) {
            return Result.failure(BusinessStatus.CHECK_ERROR.getCode(), "导入文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        if (!FilenameUtils.isExtension(originalFilename, new String[]{"xls", "xlsx"})) {
            return Result.failure(BusinessStatus.CHECK_ERROR.getCode(), "导入文件格式不正确");
        }
        // 文件大小的限制。。。
        List<String> titles = new ArrayList<>();
        titles.add("用户名");
        titles.add("年龄");
        titles.add("邮箱");
        titles.add("出生日期");
        titles.add("薪水");
        String[][] result = ExcelUtils.read(titles, file.getInputStream(), "用户信息", 2);
        // 校验result
        for (int i = 0; i < result.length; i++) {
            String[] rows = result[i];
            if (rows[0].length() > 30) {
                return Result.failure(BusinessStatus.CHECK_ERROR.getCode(), "用户名长度超过限制");
            }
            // .......
        }
        // 将String[][]转换成List<T>
        List<User> users = new ArrayList<>(result.length);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < result.length; i++) {
            String[] rows = result[i];
            Date birthday = sdf.parse(rows[3]);
            User user = new User(rows[0], Integer.parseInt(rows[1]),
                    rows[2], birthday, new BigDecimal(rows[4]));
            users.add(user);
        }
        System.out.println(users);
        return Result.success("导入成功");
    }

    /**
     * 导出
     *
     * @param user
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportUser", method = RequestMethod.POST)
    @ApiOperation(value = "导出用户信息", notes = "导出用户信息", httpMethod = "POST")
    public void exportUser(User user, HttpServletResponse response) throws IOException {
        String filename = "用户信息.xlsx";
        filename = URLEncoder.encode(filename, "UTF-8");
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachement; filename=" + filename + "; filename*=utf-8''" + filename);
        List<UserExporter> records = new ArrayList<>();
        records.add(new UserExporter("yiyi", 10, "yiyi@qq.com", new Date(), new BigDecimal(10000.01)));
        records.add(new UserExporter("erer", 11, "erer@qq.com", new Date(), new BigDecimal(10001.01)));
        records.add(new UserExporter("sasa", 12, "sasa@qq.com", new Date(), new BigDecimal(10002.06)));
        OutputStream os = response.getOutputStream();
        ExcelUtils.write(records, UserExporter.class, "用户信息", os);
        os.close();
    }

    @ApiOperation(value = "文件上传", notes = "文件上传", httpMethod = "POST")
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Result<Object> upload(MultipartFile file) throws IOException {
        if (null == file) {
            return Result.failure(BusinessStatus.CHECK_ERROR.getCode(), "导入文件不能为空");
        }
        // 校验文件类型、文件大小
        /*String originalFilename = file.getOriginalFilename();
        if (!FilenameUtils.isExtension(originalFilename, new String[]{"xls", "xlsx"})) {
            return Result.failure(BusinessStatus.CHECK_ERROR.getCode(), "导入文件格式不正确");
        }*/
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = "/upload" + File.separator + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + File.separator + UUID.randomUUID() + "." + ext;
        File target = new File(filename);
        File parent = target.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        OutputStream os = new FileOutputStream(filename);
        IOUtils.copy(file.getInputStream(), os);
        os.close();
        return Result.success("文件上传成功");
    }

    /**
     * 下载
     *
     * @param filename
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "文件下载", notes = "文件下载", httpMethod = "GET")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(String filename, HttpServletResponse response) throws IOException {
        String realFile = "E:\\test.xlsx";
        filename = URLEncoder.encode(filename + realFile.substring(realFile.lastIndexOf(".")), "UTF-8");
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachement; filename=" + filename + "; filename*=utf-8''" + filename);
        IOUtils.copy(new FileInputStream("E:\\test.xlsx"), response.getOutputStream());
    }

    @ApiOperation(value = "加载图片", notes = "加载图片", httpMethod = "GET", hidden = true)
    @RequestMapping(value = "/loadPic", method = RequestMethod.GET)
    public void loadPic(String filename, HttpServletResponse response) throws IOException {
        String picPath = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1511019510&di=ed8a9d5104202c1abebfde4f38f2bfb2&src=http://image.tianjimedia.com/uploadImages/2015/215/45/04L5VRR21C5W.jpg";
        OutputStream os = response.getOutputStream();
        URL url = new URL(picPath);
        InputStream is = url.openStream();
        IOUtils.copy(is, os);
        is.close();
        os.close();
    }
}
