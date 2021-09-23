package com.oracleclub.server.entity.param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author :RETURN
 * @date :2021/9/22 8:21
 */
@Data
public class PageRequest implements Serializable {
    private final Integer page;
    private final Integer size;
    //todo 排序参数待定使用
    private final Sort sort;

    public static PageRequest of(int page, int size) {
        return new PageRequest(page,size,null);
    }

    public Optional<PageRequest> toOptional(){
        return Optional.of(this);
    }

    public <T> IPage<T> convertTo(){

        return new Page<>(this.page, this.size);
    }

    public enum Sort{
        //升序
        ASC,
        //降序
        DESC;

        private Sort() {
        }

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }

        public static Sort formatFromStr(String str){
            try {
                return valueOf(str.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        String.format("在转换的过程中出现非法的参数'%s' 参数仅仅只能是'asc'或'desc'", str),
                        e);
            }
        }
    }
}
