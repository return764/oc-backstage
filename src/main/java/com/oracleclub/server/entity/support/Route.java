package com.oracleclub.server.entity.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/10/11 23:13
 */
@Data
@JsonInclude(Include.NON_NULL)
public class Route {
    private String path;
    private String component;
    private String name;
    private RouteMeta meta;
    private String redirect;
    private List<Route> children;

    @Data
    @Builder
    @JsonInclude(Include.NON_NULL)
    public static class RouteMeta{
        private String name;
        private String parent;
        private Boolean invisible;
    }

    public void of(){

    }

    public Route wrap(String path, String name){
        Route wrapped = new Route();
        wrapped.setPath(path);
        wrapped.setName(name);
        wrapped.setChildren(new ArrayList<>(Collections.singletonList(this)));

        return wrapped;
    }

    public Route wrap(String path){
        return wrap(path, null);
    }

    public Route wrap(Route wrapped){
        wrapped.children.add(this);

        return wrapped;
    }
}

