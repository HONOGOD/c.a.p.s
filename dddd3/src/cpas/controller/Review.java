package cpas.controller;

public class Review {
    private String user_id; // 변경된 부분
    private String user_review;
    private String user_rating;
    private String store_name; // 추가한 store_name 필드

    public Review() {
    }

    public Review(String user_id, String user_review, String user_rating, String store_name) {
        this.user_id = user_id;
        this.user_review = user_review;
        this.user_rating = user_rating;
        this.store_name = store_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_review() {
        return user_review;
    }

    public void setUser_review(String user_review) {
        this.user_review = user_review;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
    
}
