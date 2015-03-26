package com.wafersystems.mrbs.web.evaluation;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.listener.BaseDataServiceListener;
import com.wafersystems.mrbs.service.meeting.EvaluationService;
import com.wafersystems.mrbs.service.user.UserTypeService;
import com.wafersystems.mrbs.vo.meeting.Evaluation;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="/evaluation")
public class EvaluationController {

	private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);


	private EvaluationService evaluationService;
	private BaseDataServiceListener serviceListener;
	private UserTypeService userTypeService;

	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, Model model) {
		List<Evaluation> datas;
		PageSortModel psm = new PageSortModel(request); //eXtremeTable
		datas = evaluationService.getEvaluationList(psm);
		request.setAttribute(GlobalConstent.REPORT_DATA, datas);
		return "evaluation/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("USER_TYPE", userTypeService.getAll());
		return "evaluation/add";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(@RequestParam Short userType,
			@RequestParam String name,@RequestParam String title1,@RequestParam String title2,
			@RequestParam String title3,@RequestParam String title4,@RequestParam String title5,
			HttpServletRequest request,
			Model model)  {
		    Evaluation eva = new Evaluation();
		    eva.setName(name);
		    eva.setTitle1(title1);
		    eva.setTitle2(title2);
		    eva.setTitle3(title3);
		    eva.setTitle4(title4);
		    eva.setTitle5(title5);
		    eva.setUserType(userTypeService.getUserTypeByValue(userType));
            evaluationService.saveEvaluation(eva);
			logger.debug("评价添加成功， 名称=" + eva.getName());
			return list(request ,model);
	}
	
	@RequestMapping(value="/edit/{evaluationId}", method=RequestMethod.GET)
	public String edit(@PathVariable Integer evaluationId, Model model) {
		Evaluation evaluation = evaluationService.getEvaluationById(evaluationId);
		model.addAttribute("evaluation", evaluation);
		model.addAttribute("USER_TYPE", userTypeService.getAll());
		return "evaluation/edit";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@RequestParam Integer id,@RequestParam Short userType,
			@RequestParam String name,@RequestParam String title1,@RequestParam String title2,
			@RequestParam String title3,@RequestParam String title4,@RequestParam String title5,
			HttpServletRequest request,
			Model model) {	
		    Evaluation eva = new Evaluation();
		    eva.setId(id);
		    eva.setName(name);
		    eva.setTitle1(title1);
		    eva.setTitle2(title2);
		    eva.setTitle3(title3);
		    eva.setTitle4(title4);
		    eva.setTitle5(title5);
		    eva.setUserType(userTypeService.getUserTypeByValue(userType));
			evaluationService.updateEvaluation(eva);
//			serviceListener.loadPara();
			return list(request, model);
	}
	
	@RequestMapping(value="/delete/{evaluationId}", method=RequestMethod.GET)
	public String delete(@PathVariable Integer evaluationId,  HttpServletRequest request, Model model) {
			evaluationService.delEvaluation(evaluationId);
			serviceListener.loadPara();
			return list(request, model);
	}
	
	@RequestMapping(value="/rated")
	public String rated(HttpServletRequest request, Model model) {
		List<EvaluationRateModel> ermList = new ArrayList<EvaluationRateModel>();
		double avgScore = 4,avgPercent = 0, d5 = 0, d4 = 0, d3 = 0, d2 = 0, d1 = 0;
		String starScore = null;
		//取主评价
		List<Evaluation> mainEvalution = evaluationService.getMainEvaluationList();
		for (Evaluation mainevaluation : mainEvalution) {	
			Long evaluationMeetNumberAll = evaluationService.getMainEvaluationMeetNumberByid();
			if(evaluationMeetNumberAll>0)
			{
				EvaluationRateModel erm = new EvaluationRateModel();	
				Long evaluationMeetNumberBy5 = evaluationService.getMainEvaluationMeetNumberByScore(5);
				Long evaluationMeetNumberBy4 = evaluationService.getMainEvaluationMeetNumberByScore(4);
				Long evaluationMeetNumberBy3 = evaluationService.getMainEvaluationMeetNumberByScore(3);
				Long evaluationMeetNumberBy2 = evaluationService.getMainEvaluationMeetNumberByScore(2);
				Long evaluationMeetNumberBy1 = evaluationService.getMainEvaluationMeetNumberByScore(1);
				Double evaluationRateAvgScore = evaluationService.getMainEvaluationRateByid();
			try
			{
				//转换评估分为星星显示的CLASS名称
		    starScore = String.valueOf(evaluationRateAvgScore);
		    starScore = starScore.replace(".","d");
		    starScore = "star-value-"+starScore;
			if(evaluationRateAvgScore>=avgScore)
			{
				erm.setHigh(true);
				avgPercent = ((evaluationRateAvgScore-avgScore)/avgScore)*100;
				
			}
			else
			{
				erm.setHigh(false);
				avgPercent = ((avgScore - evaluationRateAvgScore )/avgScore)*100;
			}
			d5 = ((double)evaluationMeetNumberBy5/(double)evaluationMeetNumberAll)*100;
			System.out.println("=====evaluationMeetNumberBy5==="+evaluationMeetNumberBy5);
			System.out.println("=========evaluationMeetNumberAll=="+evaluationMeetNumberAll);
			System.out.println("========="+d5);
			d4 = ((double)evaluationMeetNumberBy4/(double)evaluationMeetNumberAll)*100;
			d3 = ((double)evaluationMeetNumberBy3/(double)evaluationMeetNumberAll)*100;
			d2 = ((double)evaluationMeetNumberBy2/(double)evaluationMeetNumberAll)*100;
			d1 = ((double)evaluationMeetNumberBy1/(double)evaluationMeetNumberAll)*100;			
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			erm.setStarScore(starScore);
			erm.setEvaluationRate(String.valueOf(evaluationRateAvgScore));
			erm.setEvaluationRatePercent(String.valueOf(avgPercent));
			erm.setEvaluationRateAllMN(String.valueOf(evaluationMeetNumberAll));
			erm.setEvaluationRate1MN(String.valueOf(evaluationMeetNumberBy1));
			erm.setEvaluationRate1MNPercent(String.valueOf(d1));
			erm.setEvaluationRate2MN(String.valueOf(evaluationMeetNumberBy2));
			erm.setEvaluationRate2MNPercent(String.valueOf(d2));
			erm.setEvaluationRate3MN(String.valueOf(evaluationMeetNumberBy3));
			erm.setEvaluationRate3MNPercent(String.valueOf(d3));
			erm.setEvaluationRate4MN(String.valueOf(evaluationMeetNumberBy4));
			erm.setEvaluationRate4MNPercent(String.valueOf(d4));
			erm.setEvaluationRate5MN(String.valueOf(evaluationMeetNumberBy5));
			erm.setEvaluationRate5MNPercent(String.valueOf(d5));
			erm.setEvaluationName(mainevaluation.getName());
			ermList.add(erm);
			}
		}
		//取子评价
		List<Evaluation> evalution = evaluationService.getEvaluationList();
		
		for(Evaluation eval:evalution)
		{
					
			Long evaluationMeetNumberAll = evaluationService.getEvaluationMeetNumberByid(eval.getId());
			if(evaluationMeetNumberAll>0)
			{
			EvaluationRateModel erm = new EvaluationRateModel();
			Double evaluationRateAvgScore = evaluationService.getEvaluationRateByid(eval.getId());
			Long evaluationMeetNumberBy5 = evaluationService.getEvaluationMeetNumberByScore(eval.getId(),5);
			Long evaluationMeetNumberBy4 = evaluationService.getEvaluationMeetNumberByScore(eval.getId(),4);
			Long evaluationMeetNumberBy3 = evaluationService.getEvaluationMeetNumberByScore(eval.getId(),3);
			Long evaluationMeetNumberBy2 = evaluationService.getEvaluationMeetNumberByScore(eval.getId(),2);
			Long evaluationMeetNumberBy1 = evaluationService.getEvaluationMeetNumberByScore(eval.getId(),1);
			try
			{
				//转换评估分为星星显示的CLASS名称
			    starScore = String.valueOf(evaluationRateAvgScore);
			    starScore = starScore.replace(".","d");
			    starScore = "star-value-"+starScore;
			if(evaluationRateAvgScore>=avgScore)
			{
				erm.setHigh(true);
				avgPercent = (evaluationRateAvgScore-avgScore)/avgScore*100;
				
			}
			else
			{
				erm.setHigh(false);
				avgPercent = (avgScore - evaluationRateAvgScore )/avgScore*100;
			}
			d5 = ((double)evaluationMeetNumberBy5/(double)evaluationMeetNumberAll)*100;
			System.out.println("=====evaluationMeetNumberBy5==="+evaluationMeetNumberBy5);
			System.out.println("=========evaluationMeetNumberAll=="+evaluationMeetNumberAll);
			System.out.println("========="+d5);
			d4 = ((double)evaluationMeetNumberBy4/(double)evaluationMeetNumberAll)*100;
			d3 = ((double)evaluationMeetNumberBy3/(double)evaluationMeetNumberAll)*100;
			d2 = ((double)evaluationMeetNumberBy2/(double)evaluationMeetNumberAll)*100;
			d1 = ((double)evaluationMeetNumberBy1/(double)evaluationMeetNumberAll)*100;			
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			erm.setStarScore(starScore);
			erm.setEvaluationRate(String.valueOf(evaluationRateAvgScore));
			erm.setEvaluationRatePercent(String.valueOf(avgPercent));
			erm.setEvaluationRateAllMN(String.valueOf(evaluationMeetNumberAll));
			erm.setEvaluationRate1MN(String.valueOf(evaluationMeetNumberBy1));
			erm.setEvaluationRate1MNPercent(String.valueOf(d1));
			erm.setEvaluationRate2MN(String.valueOf(evaluationMeetNumberBy2));
			erm.setEvaluationRate2MNPercent(String.valueOf(d2));
			erm.setEvaluationRate3MN(String.valueOf(evaluationMeetNumberBy3));
			erm.setEvaluationRate3MNPercent(String.valueOf(d3));
			erm.setEvaluationRate4MN(String.valueOf(evaluationMeetNumberBy4));
			erm.setEvaluationRate4MNPercent(String.valueOf(d4));
			erm.setEvaluationRate5MN(String.valueOf(evaluationMeetNumberBy5));
			erm.setEvaluationRate5MNPercent(String.valueOf(d5));
			erm.setEvaluationName(eval.getName());
			ermList.add(erm);
			}
			
		}
		request.setAttribute("evalratelist", ermList);
		return "evaluation/rated";
	}

	@Resource(type = EvaluationService.class)
	public void setMeetingRoomService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@Resource(name = "initListener")
	public void setServiceListener(BaseDataServiceListener serviceListener) {
		this.serviceListener = serviceListener;
	}
	@Resource(type = UserTypeService.class)
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}
}

