package com.study.mongodb.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 上传的文件信息
 */
@Builder
@Data
public class FileInfo implements Serializable {
    private String fileName;
    private String contentType;
    private long size;
    private String md5;
}
