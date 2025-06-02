package kr.co.itwillbs.oracle.commons.constant;

public enum ItemSellStatus {
	AVAILABLE("판매중"),
	OUT_OF_STOCK("품절"),
	DISCONTINUED("단종");
	
	private final String label;

	private ItemSellStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}
