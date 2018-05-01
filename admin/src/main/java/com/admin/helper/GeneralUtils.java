package com.admin.helper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.admin.helper.vo.BaseVO;
import com.admin.to.BaseTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

public class GeneralUtils {
	public static final String DELETED = "Y";
	public static final String NOT_DELETED = "N";
	public static final String IS_PARENT = "Y";
	public static final String IS_NOT_PARENT = "N";
	public static final String ALLOW_DISPLAY = "Y";
	public static final String NOT_ALLOW_DISPLAY = "N";
	public static final String MODE_BATCH = "BATCH";
	public static final String MODE_ADHOC = "ADHOC";
	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_BAD_DEBT = "BAD DEBT";
	public static final String BOUNCED = "Y";
	public static final String UNBOUNCED = "N";
	public static final String TYPE_SALARY = "Salary";
	public static final String TYPE_SALARY_BONUS = "SalaryBonus";
	public static final String TYPE_BONUS = "Bonus";
	public static final String YES_IND = "Y";
	public static final String NO_IND = "N";
	public static final String NONE = "NONE";
	public static final String KEYWORD_STOCK_CHINA = "china";
	
	public static final String MODULE_SALARY_BONUS = "salarybonus";
	public static final String MODULE_SALARY = "salary";
	public static final String MODULE_BONUS = "bonus";
	public static final String MODULE_EXPENSE = "expense";
	public static final String MODULE_INVOICE = "invoice";
	public static final String MODULE_GRANT = "grant";
	
	public static final String PAYMENT_MODE_CASH = "Cash";
	public static final String PAYMENT_MODE_CHEQUE = "Cheque";
	public static final String PAYMENT_MODE_BY_DIRECTOR = "Pay By Director";
	public static final String PAYMENT_MODE_TO_DIRECTOR = "Pay To Director";
	public static final String PAYMENT_MODE_GIRO = "GIRO";
	
	public static final String TYPE_PRODUCT_CATEGORY = "product_category";
	public static final String TYPE_PRODUCT = "product";
	public static final String CATEGORY_PATH = "category\\";
	public static final String PRODUCT_PATH = "product\\";
	public static final String IMAGE_PATH = "\\JJ\\images\\";
	
	public static final String STANDARD_DATE_FORMAT = "dd/MM/yyyy";
	public static final String SALARY_DATE_FORMAT = "MM-yyyy";
	public static final String BONUS_DATE_FORMAT = "yyyy";
	public static final String EXCEL_DATE_MONTH_YEAR_FORMAT = "MMM-yy";
	public static final String EXCEL_DATE_YEAR_FORMAT = "yyyy";
	public static final List<String> monthList = Arrays.asList("Jan","Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"); 
	
	private static final Logger logger = Logger.getLogger(GeneralUtils.class);	
	public static <T> String convertListToJSONString(List<T> list){
		return  convertListToJSONObject(list).toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> JSONObject convertListToJSONObject(List<T> list){
		Gson gson = new GsonBuilder().serializeNulls().create();
		JsonArray result = (JsonArray) gson.toJsonTree(list,
	            new TypeToken<List<T>>() {}.getType());
		JSONObject obj = new JSONObject();
		obj.put("data", result);
		return obj;
		
	}
	
	public static <T> Map<String, T> convertListToStringMap(Collection<T> list, String attribute) {
	   Map<String, T> map = new HashMap<String, T>();
	   for (T obj : list) {
		   try{
			   String fieldValue = (String) getObjectProprty(obj, attribute);
			   map.put(fieldValue.toString(), obj);
		   }catch(Exception ex){
			   logger.error(ex.getStackTrace());
		   }
	       
	   }   
	   return map;
	}
	
	public static <T> Map<String, List<T>> convertListToStringListMap(Collection<T> list, String attribute) {
	   Map<String, List<T>> map = new HashMap<String, List<T>>();
	   if(list != null){
		   for (T obj : list) {
			   try{
				   String fieldValue = (String) getObjectProprty(obj, attribute);
				   if(!map.containsKey(fieldValue.toString())){
					   map.put(fieldValue.toString(), new ArrayList<T>());
				   }
				   map.get(fieldValue.toString()).add(obj);
			   }catch(Exception ex){
				   logger.error(ex.getMessage(),ex);
			   }
		       
		   }
	   }
	   return map;
	}
	
	public static <T> Map<Long, T> convertListToLongMap(List<T> list, String attribute) {
	   Map<Long, T> map = new HashMap<Long, T>();
	   if(list != null){
		   for (T obj : list) {
			   try{
				   Long fieldValue = (Long)getObjectProprty(obj, attribute);
				   map.put(fieldValue, obj);
			   }catch(Exception ex){
				   ex.printStackTrace();
				   logger.error(ex.getMessage());
			   }
		       
		   }  
	   }
	   return map;
	}
	
	public static <T> Map<Integer, T> convertListToIntegerMap(List<T> list, String attribute) {
	   Map<Integer, T> map = new HashMap<Integer, T>();
	   for (T obj : list) {
		   try{
			   Integer fieldValue = (Integer)getObjectProprty(obj, attribute);
			   map.put(fieldValue, obj);
		   }catch(Exception ex){
			   ex.printStackTrace();
			   logger.error(ex.getMessage());
		   }
	       
	   }   
	   return map;
	}
	
	public static <T> Map<Integer, List<T>> convertListToIntegerListMap(List<T> list, String attribute) {
	   Map<Integer, List<T>> map = new HashMap<Integer, List<T>>();
	   for (T obj : list) {
		   try{
			   Integer fieldValue = (Integer)getObjectProprty(obj, attribute);
			   if(!map.containsKey(fieldValue)){
				   map.put(fieldValue, new ArrayList<T>());
			   }
			   map.get(fieldValue).add(obj);
		   }catch(Exception ex){
			   ex.printStackTrace();
			   logger.error(ex.getMessage());
		   }
	       
	   }   
	   return map;
	}
	
	
	public static synchronized String getHtmlRows(ResultSet results) throws SQLException{
        StringBuffer htmlRows = new StringBuffer();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        htmlRows.append("<tr>");
        for (int i = 1; i <= columnCount; i++)
            htmlRows.append("<td><b>"
                + metaData.getColumnName(i) + "</td>");
        htmlRows.append("</tr>");

        while (results.next()){
            htmlRows.append("<tr>");
            for (int i = 1; i <= columnCount; i++)
                htmlRows.append("<td>"
                    + results.getString(i) + "</td>");
        }
        htmlRows.append("</tr>");
        return htmlRows.toString();
	}
	
	public static String convertDateToString(Date date, String formatString){
		String dateString = "";
		try{
			dateString = new SimpleDateFormat(formatString).format(date);
		}catch(Exception e){
			logger.info("Error converting date to string. Format:"+ formatString + ", Date : " + ((date == null) ? "null" : date.toString()) );
		}
		return dateString;
	}
	
	public static Date convertStringToDate(String dateString, String formatString){
		Date date = null;
		try{
			date = new SimpleDateFormat(formatString).parse(dateString);
		}catch(Exception e){
			logger.info("Error converting string to date.");
		}
		return date;
	}
	
	public static BindingResult validate(Object obj, String form, BindingResult result){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		List<String> errorComponent = new ArrayList<String>();
		for (ConstraintViolation<Object> violation : violations) 
        {
			String propertyPath = violation.getPropertyPath().toString();
			if(errorComponent.contains(propertyPath)){
				continue;
			}
			errorComponent.add(propertyPath);
            String[] codes = new String[]{violation.getMessage()};
            Object[] argument = new Object[]{};
            Object rejectedValue = violation.getInvalidValue();
            result.addError(new FieldError(form,propertyPath,rejectedValue,false, codes, argument,new String()));
        }
		return result;
	}
	
	public static Date getCurrentDate(){
		return new Date();
	}
	
	public static Date getNewDate(Integer noOfYears){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, noOfYears); // to get previous year add -1
		return cal.getTime();
	}
	
	public static boolean isInteger(String id){
		try{
			Integer.valueOf(id);
			return true;
		}catch(Exception ex){
			
		}
		return false;
	}
	
	public static <T> List<String> convertListToStringList(List<T> objList, String variableName, boolean unique){
		List<String> list = new ArrayList<String>();
		if(objList != null && objList.size() > 0){
			for(T obj : objList){
				Object variableObj = getObjectProprty(obj, variableName);
				if(variableObj != null && variableObj instanceof String){
					String stringObj = (String)variableObj;
					list.add(stringObj);
				}
			}
			if(unique){
				Set<String> stringSet = new HashSet<String>();
				for(String string:list){
					stringSet.add(string);
				}
				list.clear();
				list.addAll(stringSet);
			}
			
		}
		return list;
	}
	
	public static <T> List<Long> convertListToLongList(List<T> objList, String variableName, boolean unique){
		List<Long> list = new ArrayList<Long>();
		if(objList != null && objList.size() > 0){
			for(T obj : objList){
				Object variableObj = getObjectProprty(obj, variableName);
				if(variableObj != null && variableObj instanceof Long){
					Long stringObj = (Long)variableObj;
					list.add(stringObj);
				}
			}
			
			if(unique){
				Set<Long> longSet = new HashSet<Long>();
				for(Long var:list){
					longSet.add(var);
				}
				list.clear();
				list.addAll(longSet);
			}
		}
		return list;
	}
	
	public static <T> List<BigDecimal> convertListToBigDecimalList(List<T> objList, String variableName){
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		if(objList != null && objList.size() > 0){
			for(T obj : objList){
				Object variableObj = getObjectProprty(obj, variableName);
				if(variableObj != null && variableObj instanceof BigDecimal){
					BigDecimal stringObj = (BigDecimal)variableObj;
					list.add(stringObj);
				}
			}
		}
		return list;
	}
	
	public static <T> List<Integer> convertListToIntegerList(List<T> objList, String variableName){
		List<Integer> list = new ArrayList<Integer>();
		if(objList != null && objList.size() > 0){
			for(T obj : objList){
				Object variableObj = getObjectProprty(obj, variableName);
				if(variableObj != null && variableObj instanceof Integer){
					Integer stringObj = (Integer)variableObj;
					list.add(stringObj);
				}
			}
		}
		return list;
	}
	
	public static Object getObjectProprty(Object obj, String variableName){
		try {
			return PropertyUtils.getProperty(obj, variableName);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static <T>LinkedList<T> convertArrayToLinkedList(T... array){
		LinkedList<T> list = new LinkedList<T>();
		if(array != null){
			for(T obj : array){
				list.add(obj);
			}
		}
		return list;
	}
	
	public static <T>List<T> sortByMonth(List<T> list){
		Collections.sort(list, new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				String t1Month = (String) getObjectProprty(o1, "month");
				String t2Month = (String) getObjectProprty(o2, "month");
				int t1Index = monthList.indexOf(t1Month);
				int t2Index = monthList.indexOf(t2Month);
				if(t1Index >= 0 && t2Index >= 0){
					return t1Index - t2Index;
				}else if (t1Index >= 0){
					return -1;
				}else {
					return 1;
				}
			}
			
		});
		return list;
	}
	
	public static <T>List<T> sortByVariable(List<T> list, final String variableName){
		Collections.sort(list, new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				String value1 = (String) getObjectProprty(o1, variableName);
				String value2 = (String) getObjectProprty(o2, variableName);
				if(value1 != null && value2 != null){
					return value1.compareTo(value2);
				}else if (value1 != null){
					return -1;
				}else if (value2 != null){
					return 1;
				}else {
					return 0;
				}
			}
			
		});
		return list;
	}
	
	public static <T>List<T> sortAccordingToSortList(List<T> list, final List<String> sortList, final String name){
		Collections.sort(list, new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				String t1Month = (String) getObjectProprty(o1, name);
				String t2Month = (String) getObjectProprty(o2, name);
				int t1Index = sortList.indexOf(t1Month);
				int t2Index = sortList.indexOf(t2Month);
				if(t1Index >= 0 && t2Index >= 0){
					return t1Index - t2Index;
				}else if (t1Index >= 0){
					return -1;
				}else {
					return 1;
				}
			}
			
		});
		return list;
	}
	
	public static void copyFromVO(BaseVO vo ,BaseTO baseTO){
		baseTO.setVersion(vo.getVersion());
		baseTO.setCreatedBy(vo.getCreatedBy());
		baseTO.setCreatedOn(vo.getCreatedOn());
		baseTO.setUpdatedBy(vo.getUpdatedBy());
		baseTO.setUpdatedOn(vo.getUpdatedOn());
		baseTO.setDeleteInd(vo.getDeleteInd());
	}
	
	public static void copyFromTO(BaseVO vo ,BaseTO baseTO){
		vo.setVersion(baseTO.getVersion());
		vo.setCreatedBy(baseTO.getCreatedBy());
		vo.setCreatedOn(baseTO.getCreatedOn());
		vo.setUpdatedBy(baseTO.getUpdatedBy());
		vo.setUpdatedOn(baseTO.getUpdatedOn());
		vo.setDeleteInd(baseTO.getDeleteInd());
	}
	
	public static void copyFromTO(BaseTO to ,BaseTO baseTO){
		to.setVersion(baseTO.getVersion());
		to.setCreatedBy(baseTO.getCreatedBy());
		to.setCreatedOn(baseTO.getCreatedOn());
		to.setUpdatedBy(baseTO.getUpdatedBy());
		to.setUpdatedOn(baseTO.getUpdatedOn());
		to.setDeleteInd(baseTO.getDeleteInd());
	}
}
