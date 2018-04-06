package com.study.mongodb.service;

import com.study.mongodb.model.FileInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import java.io.InputStream;

public interface FileService {
    ObjectId upload(InputStream in, FileInfo fileInfo);

    GridFsResource download(String id);

    void remove(String id);

}
