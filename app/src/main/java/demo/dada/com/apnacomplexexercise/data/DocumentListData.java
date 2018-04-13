package demo.dada.com.apnacomplexexercise.data;

public class DocumentListData {

    private String doc_name;
    private String doc_size;
    private String doc_type;
    private String doc_url;

    public DocumentListData(String doc_name,
                               String doc_size,
                               String doc_type,
                               String doc_url)
    {
        this.doc_name = doc_name;
        this.doc_size = doc_size;
        this.doc_type = doc_type;
        this.doc_url = doc_url;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public void setDoc_size(String doc_size) {
        this.doc_size = doc_size;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getDoc_size() {
        return doc_size;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public String getDoc_url() {
        return doc_url;
    }

}
