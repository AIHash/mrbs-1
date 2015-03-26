package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.ApplicationOperationStep;

public interface ApplicationOperationStepService {

	public void saveApplicationOperationStep(ApplicationOperationStep vo);

	public void updateApplicationOperationStep(ApplicationOperationStep vo);

	public void delApplicationOperationStep(ApplicationOperationStep vo);
	
	public ApplicationOperationStep getApplicationOperationStepById(int id);

}
