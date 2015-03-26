package com.wafersystems.mrbs.service.right;

import com.wafersystems.mrbs.vo.right.Function;

public interface FunctionService {

	public void saveFunction(Function vo);

	public void updateFunction(Function vo);

	public void delFunction(Function vo);
	
	public Function getFunctionById(int id);

}
