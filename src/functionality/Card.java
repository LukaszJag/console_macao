package functionality;

public class Card{

    public int value;
    public char color;
    public boolean isActionCard;

    public Card(){}

    public Card(int value, char color, boolean isActionCard){
        this.value = value;
        this.color = color;
        this.isActionCard = isActionCard;
    }

    //Introduce yourself
    public String introduceYourself(){
        return "Value: " + String.valueOf(this.value) + " Color: "
                + String.valueOf(this.color) + " isAction: " + String.valueOf(this.isActionCard);
    }

    public String prettyName(){
        String sign = String.valueOf(color) ;

        /*
        if(color == 'h'){
            sign = "♥";
        }
        if(color == 's'){
            sign = "♠";
        }
        if(color == 'd'){
            sign = "♦";
        }
        if(color == 'c'){
            sign = "♣";
        }
        */

        return "|" + String.valueOf(value) +  sign + "|";

    }
    public boolean isEmptyCard(){
        if(this.value == 0 && this. color == 'z' && this.isActionCard == false){
            return true;
        }else{
            return false;
        }
    }

    public void setEmpty(){
        this.value = 0;
        this.color = 'z';
        this.isActionCard = false;
    }
    //Getter
    public boolean getIsAction(){
        return  this.isActionCard;
    }

    //Setter
    public void setActionCard(boolean isActionCard){
        this.isActionCard = isActionCard;
    }

    //Getter
    public int getValue(){
        return this.value;
    }

    //Setter
    public void setValue(int value){
        this.value = value;
    }

    //Getter
    public char getColor(){
        return this.color;
    }

    //Setter
    public void setColor(char color){
        this.color = color;
    }
}
