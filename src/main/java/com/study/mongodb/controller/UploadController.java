package com.study.mongodb.controller;

import com.study.mongodb.model.FileInfo;
import com.study.mongodb.service.FileService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class UploadController {
    private Logger logger= LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Map<String, Object> uploadFile(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getServletContext());
        //设置编码
        multipartResolver.setDefaultEncoding("utf-8");
        //判断是否是文件
        if (multipartResolver.isMultipart(request)) {
            // 再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
            Iterator<String> iterator = multiRequest.getFileNames();
            if (iterator.hasNext()) {
                MultipartFile file = multiRequest.getFile(iterator.next());
                upload(file,map);
            }
        }
        return map;
    }

    //默认上传指定的文件
    @PostMapping("/upload2")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String,Object> map=new HashMap<>();
        if(file.isEmpty()) {
            map.put("code", "error");
            map.put("msg", "the upload file is empty");
        }else{
            upload(file,map);
        }
        return map;
    }

    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable String fileId,HttpServletResponse response){
        GridFsResource gridFsResource=fileService.download(fileId);
        OutputStream outputStream=null;
        if (gridFsResource != null)
        {
            try{
                InputStream gridStream=gridFsResource.getInputStream();
                outputStream=response.getOutputStream();
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(gridFsResource.getFilename(), "utf-8") + "\"");
                logger.info("------------下载的文件名是------"+gridFsResource.getFilename());
                byte[] buf = new byte[1024 * 100];
                int len;
                while ((len = gridStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(outputStream!=null){
                    try{
                        outputStream.flush();
                        outputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @GetMapping("/delete/{fileId}")
    public Map<String,Object> deleteFile(@PathVariable String fileId){
        Map<String, Object> map = new HashMap<>();
        if(!StringUtils.isEmpty(fileId)){
            try{
                fileService.remove(fileId);
                map.put("code","success");
                map.put("msg","file("+fileId+") is deleted successfully");
            }catch (Exception e){
                e.printStackTrace();
                map.put("code","error");
                map.put("msg","file is deleted failed");
            }
        }
        return map;
    }

    private Map<String,Object> upload(MultipartFile file,Map<String,Object> map){
        FileInfo fileInfo = null;
        try{
            fileInfo = FileInfo.builder().
                    fileName(file.getOriginalFilename()).
                    contentType(file.getContentType()).
                    size(file.getSize()).build();
            ObjectId id = fileService.upload(file.getInputStream(), fileInfo);
            fileInfo.setMd5(id.toString());
            map.put("fileInfo", fileInfo);
            map.put("code", "success");
            map.put("msg", "file upload successfully");
        }catch (Exception e){
            e.printStackTrace();
            map.put("fileInfo", fileInfo);
            map.put("code", "error");
            map.put("msg", "file upload failed");
        }
        return map;
    }
}
