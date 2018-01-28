package iota;


public class IotaApp{

    public static void main (String[] args){

        int p1wins = 0;
        int p2wins = 0;
        Manager m = new Manager();
        Player p1 = new MaxLots(m);
        Player p2 = new MaxFirst(m);
        m.addPlayers(p1,p2);
        for(int i = 0; i<1000; i++){
            
         
            m.setup();
            m.play();
            if (m.getRawScore(p1)> m.getRawScore(p2)){
                p1wins++;
            }else{
                p2wins++;
            }
        }
        System.out.println("Player1 wins : " + p1wins);
        System.out.println("Player2 wins : " + p2wins);
    }

}
