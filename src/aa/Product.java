package aa;

public class Product {
	private String top;
	private String imgUrl;
	private String productName;
	//原价
	private String productPrice;
	//优惠券
	private String couponPrice;
	//销量
	private String salesVolume;
	//优惠券后价格
	private String couponPriceAfter;
	//抢券链接
	private String grabCouponsUrl;
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getCouponPriceAfter() {
		return couponPriceAfter;
	}
	public void setCouponPriceAfter(String couponPriceAfter) {
		this.couponPriceAfter = couponPriceAfter;
	}
	public String getGrabCouponsUrl() {
		return grabCouponsUrl;
	}
	public void setGrabCouponsUrl(String grabCouponsUrl) {
		this.grabCouponsUrl = grabCouponsUrl;
	}
	public Product(String top, String imgUrl, String productName,
			String productPrice, String couponPrice, String couponPriceAfter,String salesVolume,
			 String grabCouponsUrl) {
		super();
		this.top = top;
		this.imgUrl = imgUrl;
		this.productName = productName;
		this.productPrice = productPrice;
		this.couponPrice = couponPrice;
		this.salesVolume = salesVolume;
		this.couponPriceAfter = couponPriceAfter;
		this.grabCouponsUrl = grabCouponsUrl;
	}
	
	

}
