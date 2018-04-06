package com.study.mongodb.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.study.mongodb.model.FileInfo;
import com.study.mongodb.service.FileService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private GridFsOperations gridFsOperations;
    @Resource
    private MongoDbFactory mongodbFactory;

    @Override
    public ObjectId upload(InputStream in, FileInfo fileInfo) {
        DBObject metaData = new BasicDBObject();
        metaData.put("fileName", fileInfo.getFileName());
        metaData.put("fileType", fileInfo.getContentType());
        return gridFsOperations.store(in,fileInfo.getFileName(),fileInfo.getContentType(),metaData);
    }

    @Override
    public GridFsResource download(String id) {
        //spring-data-mongodb 2.x和1.x有差别(返回值GridFSFile不同)
        GridFSFile gridFSFile=gridFsOperations.findOne(Query.query(Criteria.where("_id").is(id)));
        //通过ID获取资源文件
        InputStream downloadStream = GridFSBuckets.create(mongodbFactory.getDb()).openDownloadStream(gridFSFile.getObjectId());
        GridFsResource resource = new GridFsResource(gridFSFile, downloadStream);
        return resource;
    }

    @Override
    public void remove(String id) {
        gridFsOperations.delete(Query.query(Criteria.where("_id").is(id)));
    }
}
