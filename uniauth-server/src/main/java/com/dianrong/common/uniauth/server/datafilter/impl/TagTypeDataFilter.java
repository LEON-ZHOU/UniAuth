package com.dianrong.common.uniauth.server.datafilter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianrong.common.uniauth.server.data.entity.TagType;
import com.dianrong.common.uniauth.server.data.entity.TagTypeExample;
import com.dianrong.common.uniauth.server.data.mapper.TagTypeMapper;
import com.dianrong.common.uniauth.server.datafilter.FilterData;
import com.dianrong.common.uniauth.server.util.CheckEmpty;
import com.dianrong.common.uniauth.server.util.TypeParseUtil;
import com.dianrong.common.uniauth.server.util.UniBundle;

/**
 * Created by Arc on 15/4/2016.
 */

@Service("tagTypeDataFilter")
public class TagTypeDataFilter extends CurrentAbstractDataFilter {
	/**.
	 * tagType处理的mapper
	 */
	@Autowired
    private TagTypeMapper tagTypeMapper;

    /**.
     * 判断某几个字段是否同时存在.
     */
    @Override
    protected boolean dataWithConditionsEqualExist(FilterData... equalsField){
        //判空处理
        if(equalsField == null || equalsField.length == 0) {
            return false;
        }
        //首先根据类型和值获取到对应的model数组
        TagTypeExample condition = new TagTypeExample();
        TagTypeExample.Criteria criteria =  condition.createCriteria();
        //构造查询条件
        for(FilterData fd: equalsField){
            switch(fd.getType()) {
                case FIELD_TYPE_CODE:
                    criteria.andCodeEqualTo(TypeParseUtil.parseToStringFromObject(fd.getValue()));
                    break;
                case FIELD_TYPE_DOMAIN_ID:
                    criteria.andDomainIdEqualTo(Integer.parseInt(TypeParseUtil.parseToLongFromObject(fd.getValue()).toString()));
                    break;
                default:
                    break;
            }
        }
        //查询
        int count = tagTypeMapper.countByExample(condition);
        if(count > 0){
            return true;
        }
        return false;
    }

	@Override
	protected String getProcessTableName() {
		return  UniBundle.getMsg("data.filter.table.name.tagtype");
	}
	
	@Override
	protected Object getRecordByPrimaryKey(Integer id) {
		CheckEmpty.checkEmpty(id, "tagId");
		TagTypeExample condition = new TagTypeExample();
		condition.createCriteria().andIdEqualTo(id);
		List<TagType> selectByExample = tagTypeMapper.selectByExample(condition);
		
		if(selectByExample != null && !selectByExample.isEmpty()){
			return selectByExample.get(0);
		}
		return null;
	};
}
