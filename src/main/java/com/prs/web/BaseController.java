package com.prs.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseController {
	public static <T> List<T> getReturnArray (T obj){
        ArrayList<T> uList = new ArrayList<T>();
        if (obj!=null)
        	uList.add(obj);
        return uList;
	}

	public static <T> List<T> getReturnArray (Optional<T> obj){
        ArrayList<T> uList = new ArrayList<T>();
        if (obj.isPresent())
        	uList.add(obj.get());
        return uList;
	}

}