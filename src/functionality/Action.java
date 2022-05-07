package functionality;

public class Action {

    UI gameUI = new UI();
    public boolean isActionCardActive;
    public int action;
    public int witchPlayerIsWaiting;
    public int howManyCycleOfWaiting;
    public int volumeOfAction;
    public int typeOfAction;
    public char demandColor;


    public int demandValue;
    public int cycleOfDemandValue;
    public int cycleOfDemandColor;

    public Action() {
        this.witchPlayerIsWaiting = -1;
        this.howManyCycleOfWaiting = 0;
        this.volumeOfAction = 0;
        this.typeOfAction = 0;
        this.demandColor = 'z';
        this.demandValue = 0;
        this.cycleOfDemandValue = 0;
        this.cycleOfDemandColor = 0;
    }

    public boolean isActionCardActive() {
        return isActionCardActive;
    }

    public void setActionCardActive(boolean actionCardActive) {
        isActionCardActive = actionCardActive;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getWitchPlayerIsWaiting() {
        return witchPlayerIsWaiting;
    }

    public void setWitchPlayerIsWaiting(int witchPlayerIsWaiting) {
        this.witchPlayerIsWaiting = witchPlayerIsWaiting;
    }

    public int getHowManyCycleOfWaiting() {
        return howManyCycleOfWaiting;
    }

    public void setHowManyCycleOfWaiting(int howManyCycleOfWaiting) {
        this.howManyCycleOfWaiting = howManyCycleOfWaiting;
    }

    public int getVolumeOfAction() {
        return volumeOfAction;
    }

    public void setVolumeOfAction(int volumeOfAction) {
        this.volumeOfAction = volumeOfAction;
    }

    public int getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(int typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public char getDemandColor() {
        return demandColor;
    }

    public void setDemandColor(char demandColor) {
        this.demandColor = demandColor;
    }

    public int getDemandValue() {
        return demandValue;
    }

    public void setDemandValue(int demandValue) {
        this.demandValue = demandValue;
    }

    public int getCycleOfDemandValue() {
        return cycleOfDemandValue;
    }

    public void setCycleOfDemandValue(int cycleOfDemandValue) {
        this.cycleOfDemandValue = cycleOfDemandValue;
    }

    public int getCycleOfDemandColor() {
        return cycleOfDemandColor;
    }

    public void setCycleOfDemandColor(int cycleOfDemandColor) {
        this.cycleOfDemandColor = cycleOfDemandColor;
    }

    public boolean isThisPlayerWait(int currentPlayerIndex, Action action) {
        if (action.witchPlayerIsWaiting == currentPlayerIndex) {
            action.howManyCycleOfWaiting--;

            if (action.howManyCycleOfWaiting <= 0) {
                action.typeOfAction = 0;
                action.witchPlayerIsWaiting = -1;
            }

            return true;

        } else {

            return false;
        }

    }
}
