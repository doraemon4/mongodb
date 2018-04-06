package com.study.mongodb.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class FileInfo implements Serializable {
    private String fileName;
    private String contentType;
    private long size;
    private String md5;
}
