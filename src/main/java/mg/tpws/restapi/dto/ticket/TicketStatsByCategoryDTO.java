package mg.tpws.restapi.dto.ticket;

public class TicketStatsByCategoryDTO {

    private String categoryName;
    private Long total;

    public TicketStatsByCategoryDTO(String categoryName, Long total) {
        this.categoryName = categoryName;
        this.total = total;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getTotal() {
        return total;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
