package com.wafersystems.mrbs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.service.LicenseService;

@Service
public class LicenseServiceImpl implements LicenseService {

	private static final Logger log = LoggerFactory.getLogger(LicenseServiceImpl.class);

	// Version read From DB When Init
	private String softVersion = "WaferMRBS2.0";

	private boolean hasRegisted;

	// private License lic = new License();

	@Override
	public boolean checkLicense() {

		// hasRegisted = lic.checkSoftRegister(softVersion);
		// if (!hasRegisted)
		// log.error("Software is not registed, please contact provider.");
		//
		// if (hasRegisted)
		// {
		// String endTimestr = lic.getStrExt(softVersion, 0);
		// log.info("reg time is [" + endTimestr + "]");
		// log.info("System cur time is [" + new Date() + "]");
		// if (!StrUtil.isEmptyStr(endTimestr))
		// {
		// try
		// {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// if (sdf.parse(endTimestr).before(new Date()))
		// {
		// log.error("Software License was overdue!");
		// hasRegisted = false;
		// }
		// }
		// catch (Exception e)
		// {
		// log.error("License日期检查错误：", e);
		// }
		// }
		// }
		//
		// return hasRegisted;
		return true;
	}

}
