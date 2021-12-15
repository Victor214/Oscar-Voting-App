package Model;


public class FilmeItem {
    private int id;
    private String img; // URL da Imagem
    private String nome;
    private String genero;

    public FilmeItem() {

    }

    public FilmeItem(int id, String img, String nome, String genero) {
        this.id = id;
        this.img = img;
        this.nome = nome;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
