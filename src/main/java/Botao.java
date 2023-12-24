import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Botao extends Button{  // Classe para criar os botões do Menu. Herda da classe Button do javafx
    // private final String estilo = "-fx-background-color: TRANSPARENT; -fx-background-image: 'SpaceInvaders2022_1/src/main/resources/button.png';";
    
    public Botao(String textoBotao) {  // Constrói o Botão
        setText(textoBotao);
        setFonteBotao(); 
        setEstiloBotao();
        setCursor(Cursor.HAND);
        setPrefWidth(200);  // Width da imagem do botão
        setPrefHeight(50);  // Height da imagem do botão
    }

    private void setFonteBotao() {  // Define o estilo do texto do botão
        setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25));

    }

    private void setEstiloBotao() {  // Define o estilo do botão
        
    }

}
