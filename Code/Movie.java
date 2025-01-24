package Code;

public class Movie {
    public String title;
    public String genre;

    Movie(String s, String g){
        this.title = s;
        this.genre = g;
    }
    Movie(){
        this.title = "";
        this.genre = "";
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    }
