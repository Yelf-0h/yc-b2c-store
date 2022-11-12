package com.yecheng.product.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.product.mapper.PictureMapper;
import com.yecheng.pojo.Picture;
import com.yecheng.product.service.PictureService;

/**
 * (Picture)表服务实现类
 *
 * @author makejava
 * @since 2022-11-13 03:11:11
 */
@Service("pictureService")
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

}
