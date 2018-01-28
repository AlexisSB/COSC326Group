package iota;


public class IotaApp{

    public static void main (String[] args){

        int p1wins = 0;
        int p2wins = 0;
        Manager m = new Manager();
        // Put in your players here
        Player p1 = new MaxLots(m);
        Player p2 = new MaxFirst(m);
        //Change number of tests here
        int numberOfRounds = 1000;
        m.addPlayers(p1,p2);
        for(int i = 0; i<numberOfRounds; i++){
            
            m.setup();
            m.play();
            if (m.getRawScore(p1)> m.getRawScore(p2)){
                p1wins++;
            }else{
                p2wins++;
            }
        }
        System.out.println(p1.getName()  + "  wins : " + p1wins);
        System.out.println(p2.getName() + "  wins: " + p2wins);
    }

}
