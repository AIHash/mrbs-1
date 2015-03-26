package com.wafersystems.mrbs.service.param;

import com.wafersystems.mrbs.vo.param.UnifiedParameter;

public interface UnifiedParameterService {

	public void saveUnifiedParameter(UnifiedParameter vo);

	public void updateUnifiedParameter(UnifiedParameter vo);

	public void delUnifiedParameter(UnifiedParameter vo);
	
	public UnifiedParameter getUnifiedParameterById(int id);

}
