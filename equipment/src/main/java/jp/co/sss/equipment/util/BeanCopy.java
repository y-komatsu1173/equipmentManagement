package jp.co.sss.equipment.util;

import jp.co.sss.equipment.entity.StockMaster;
import jp.co.sss.equipment.form.EquipmentForm;

/**
 * Beanコピー用クラス
 * 
 * @author 小松原　2025/12/04
 *
 */
public class BeanCopy {

	/**
	 * Formクラスの各フィールドの値をエンティティ(StockTypeMaster)にコピー
	 *
	 * @param form
	 *            入力された備品分類情報
	 * @return エンティティ
	 */
	public static StockMaster copyFormToStockMaster(EquipmentForm equipmentRegistForm) {
		StockMaster entity = new StockMaster();	
		entity.setStockType(equipmentRegistForm.getCategoryId());
		entity.setName(equipmentRegistForm.getEquipmentName());
		entity.setModel(equipmentRegistForm.getModel());
		entity.setMaker(equipmentRegistForm.getMaker());
		entity.setRentFlag(equipmentRegistForm.getRentFlag());
		entity.setOwnershipType(equipmentRegistForm.getOwnershipType());
		entity.setLeaseReturnDate(equipmentRegistForm.getLeaseReturnDate());
		entity.setRemarks(equipmentRegistForm.getRemarks());		
		return entity;
	}
	
	/**
	 * Formクラスの各フィールドの値をエンティティ(EquipmentUpdateForm)にコピー
	 *
	 * @param form
	 *            入力された備品分類情報
	 * @return エンティティ
	 */
	public static EquipmentForm copyDtoToUpdateForm(StockMaster stockMaster) {
		EquipmentForm form = new EquipmentForm();

	    form.setCategoryId(stockMaster.getStockType());
	    form.setEquipmentName(stockMaster.getName());
	    form.setModel(stockMaster.getModel());
	    form.setMaker(stockMaster.getMaker());
	    form.setOwnershipType(stockMaster.getOwnershipType());
	    form.setLeaseReturnDate(stockMaster.getLeaseReturnDate());
	    form.setRemarks(stockMaster.getRemarks());
	    form.setRentFlag(stockMaster.getRentFlag());

	    return form;
	}
}
