package tp.tpsparql.jsonRequests;

import java.util.List;

public class DataRequest {

    private Integer limit;
    private List<String> fields;

    public DataRequest() {

    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "DataRequest{" +
                "limit=" + limit +
                ", fields=" + fields +
                '}';
    }
}
