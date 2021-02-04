package com.company;

import java.util.*;

abstract class player{
    private int c=0;

    int hp;
    int livestatus;
    String type;
    public player(int hp, int livestatus,String type) {
        this.hp=hp;
        this.livestatus=livestatus;
        this.type=type;
    }
    abstract int Userid(int n,int r,int a);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLivestatus() {
        return livestatus;
    }

    public void setLivestatus(int livestatus) {
        this.livestatus = livestatus;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    static class mafia extends player{
        private int id=0;
        final String type;
        public mafia(int hp, int livestatus,String type) {
            super(hp,livestatus,type);
            this.hp=hp;
            this.livestatus=livestatus;
            this.type=type;
        }

        @Override
        int Userid(int n, int r, int a) {
            return a;
        }


        public void mafiapower(int nc,int victimhp){
            setHp(hp-victimhp/nc);
        }
    }
    static class detector extends player{
        private int id=0;

        final String type;
        public detector(int hp,int livestatus,String type) {
            super(hp,livestatus,type);
            this.hp=hp;
            this.livestatus=livestatus;
            this.type=type;
        }

        @Override
        int Userid(int n, int r, int a) {
            return a;
        }


    }
    static class healer extends player{
        private int  id=0;

        final String type;
        public healer(int hp,int livestatus,String type) {
            super(hp,livestatus,type);
            this.hp=hp;
            this.livestatus=livestatus;
            this.type=type;
        }

        @Override
        int Userid(int n, int r, int a) {
            return a;
        }

    }
    static class common extends player{
        private int id=0;

        final String type;
        public common(int hp,int livestatus,String type) {
            super(hp,livestatus,type);
            this.hp=hp;
            this.livestatus=livestatus;
            this.type=type;
        }

        @Override
        int Userid(int n, int r, int a) {
            return a;
        }

    }
}
class gameM {
    Map<Integer, player> players = new HashMap<>();
    public static int y=0;
    public static int t=0;
    gameM(int n) {
        String str[] = {"Commoner", "Detector", "Healer", "Mafia"};
        int y = n / 5;
        int p = Math.max(1, n / 10);
        for (int i = 0; i < y; i++) {
            Random r = new Random();
            int id = r.nextInt(n - 1) + 1;
            String st = "Mafia";
            int livestatus = 1;
            int hp = 2500;
            if (players.get(id)==null) {
                players.put(id, new player.mafia(hp, livestatus, st));
            } else {
                i -= 1;
            }
        }
        for (int i = 0; i < y; i++){
            Random r = new Random();
            int id = r.nextInt(n - 1) + 1;
            String st = "Detector";
            int livestatus = 1;
            int hp = 800;
            if (players.get(id)==null) {
                players.put(id, new player.mafia(hp, livestatus, st));
            } else {
                i -= 1;
            }
        }
        for (int i = 0; i < p; i++) {
            Random r = new Random();
            int id = r.nextInt(n - 1) + 1;
            String st = "Healer";
            int livestatus = 1;
            int hp = 800;
            if (players.get(id)==null) {
                players.put(id, new player.mafia(hp, livestatus, st));
            } else {
                i -= 1;
            }
        }
        for (int i = 1; i < n+1; i++) {
            String st = "Common";
            int livestatus = 1;
            int hp = 1000;
            if (players.get(i)==null) {

                players.put(i, new player.mafia(hp, livestatus, st));
            }
        }
    }
    public gameM() {

    }
    public void endgame(int n){

    }
    public void round(int n,int r,int a,int userid) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        System.out.println("Round " + r + ":");
        for (int i = 0; i < n; i++) {
            if (players.get(i + 1).getLivestatus() == 1) {
                count += 1;
            }
        }
        System.out.println(count + " players are remaining:");
        for (int i = 0; i < n; i++) {
            if (players.get(i + 1).getLivestatus() == 1) {
                System.out.print("Player" + (i + 1) + " ");
            }
        }
        if (a == 1) {
            int o;
            System.out.println();
            if(players.get(userid).getLivestatus()==1) {
                System.out.println("Choose a target: ");
                o = sc.nextInt();
            }
            else{
                System.out.println("Mafias has choosen their target");
                Random rp=new Random();
                int nj= rp.nextInt(n-1)+1;
                while (players.get(nj).getType().equals("Mafia")||players.get(nj).getLivestatus()==0){
                    nj= rp.nextInt(n-1)+1;
                }
                o=nj;
            }
            int x = 0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")) {
                    x += players.get(i + 1).getHp();
                }
            }
            int tragethp=players.get(o).getHp();
            if (players.get(o).getHp() - x < 0) {
                players.get(o).setHp(0);
                players.get(o).setLivestatus(0);
            } else {
                players.get(o).setHp(players.get(o).getHp() - x);
            }
            int mjt=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    mjt+=1;
                }
            }
            int up=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    player.mafia m = new player.mafia(players.get(i + 1).getHp(), 1, "Mafia");
                    if (players.get(i+1).getHp()-(tragethp / mjt) >= 0) {
                        m.mafiapower(mjt, tragethp);
                        players.get(i+1).setHp(m.getHp());
                        if(m.getHp()>up){
                            m.setHp(players.get(i+1).getHp()-up);
                        }
                    }
                    else{
                        up+=players.get(i+1).getHp()-(tragethp/mjt);
                        players.get(i+1).setHp(0);
                    }
                }
            }
            System.out.println("Detectives have chosen a player to test.");
            Random ap=new Random();
            int towardvote=0;
            int io= ap.nextInt(n - 1) + 1;
            while (players.get(io).getType().equals("Detector")||players.get(io).getLivestatus()==0){
                io=ap.nextInt(n-1)+1;
            }
            if (players.get(io).getType().equals("Mafia")){
                players.get(io).setLivestatus(0);
                System.out.println("Player "+io+" is a mafia.");
                t+=1;
            }

            System.out.println("Healers have chosen someone to heal.");
            Random healer=new Random();
            int someone= healer.nextInt(n - 1) + 1;
            while (players.get(someone).getLivestatus()==0) {
                someone= healer.nextInt(n - 1) + 1;
            }
            if(someone==o){
                int iu=players.get(someone).getHp();
                players.get(someone).setHp(iu+500);
                players.get(someone).setLivestatus(1);
            }
            int iu=players.get(someone).getHp();
            players.get(someone).setHp(iu+500);
            System.out.println("--End of actions--");
            if(players.get(o).getLivestatus()==0){
                System.out.println("Player"+o +" has died.");
                y+=1;
            }
            voting(n,userid);
            System.out.println("--End of round "+r+"--");
        }

        else if (a == 2) {
            int o;

            System.out.println();
            System.out.println("Mafias has choosen their target");
            Random rp=new Random();
            int nj= rp.nextInt(n-1)+1;
            while (players.get(nj).getType().equals("Mafia")||players.get(nj).getLivestatus()==0){
                nj= rp.nextInt(n-1)+1;
            }
            o=nj;
            int x = 0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")) {
                    x += players.get(i + 1).getHp();
                }
            }
            int tragethp=players.get(o).getHp();
            if (players.get(o).getHp() - x < 0) {
                players.get(o).setHp(0);
                players.get(o).setLivestatus(0);
            } else {
                players.get(o).setHp(players.get(o).getHp() - x);
            }
            int mjt=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    mjt+=1;
                }
            }
            int up=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    player.mafia m = new player.mafia(players.get(i + 1).getHp(), 1, "Mafia");
                    if (players.get(i+1).getHp()-(tragethp / mjt) >= 0) {
                        m.mafiapower(mjt, tragethp);
                        players.get(i+1).setHp(m.getHp());
                        if(m.getHp()>up){
                            m.setHp(players.get(i+1).getHp()-up);
                        }
                    }
                    else{
                        up+=players.get(i+1).getHp()-(tragethp/mjt);
                        players.get(i+1).setHp(0);
                    }
                }
            }int towardvote = 0;
            int io;
            if (players.get(userid).getLivestatus()==1) {
                System.out.println("Detectives choose a player to test.");
                Random ap = new Random();
                io = sc.nextInt();
                while (players.get(io).getType().equals("Detector") || players.get(io).getLivestatus() == 0) {
                    io = sc.nextInt();
                }
            }
            else{
                System.out.println("Detectives choose a player to test.");
                Random ap = new Random();
                io = ap.nextInt(n-1)+1;
                while (players.get(io).getType().equals("Detector") || players.get(io).getLivestatus() == 0) {
                    io = ap.nextInt(n-1)+1;
                }
            }
            if (players.get(io).getType().equals("Mafia")){
                players.get(io).setLivestatus(0);
                System.out.println("Player "+io+" is a mafia.");
                t+=1;
            }
            else{
                System.out.println("Player "+io+" is a not mafia.");
            }
            System.out.println("Healers have chosen someone to heal.");
            Random healer=new Random();
            int someone= healer.nextInt(n - 1) + 1;
            while (players.get(someone).getLivestatus()==0) {
                someone= healer.nextInt(n - 1) + 1;
            }
            if(someone==o){
                int iu=players.get(someone).getHp();
                players.get(someone).setHp(iu+500);
                players.get(someone).setLivestatus(1);
            }
            int iu=players.get(someone).getHp();
            players.get(someone).setHp(iu+500);
            System.out.println("--End of actions--");
            if(players.get(o).getLivestatus()==0){
                System.out.println("Player"+o +" has died.");
                y+=1;
            }
            voting(n,userid);
            System.out.println("--End of round "+r+"--");
        }

        else if (a == 3) {

            int o;
            System.out.println();
            System.out.println("Mafias has choosen their target");
            Random rp=new Random();
            int nj= rp.nextInt(n-1)+1;
            while (players.get(nj).getType().equals("Mafia")||players.get(nj).getLivestatus()==0){
                nj= rp.nextInt(n-1)+1;
            }
            o=nj;
            int x = 0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")) {
                    x += players.get(i + 1).getHp();
                }
            }
            int tragethp=players.get(o).getHp();
            if (players.get(o).getHp() - x < 0) {
                players.get(o).setHp(0);
                players.get(o).setLivestatus(0);
            } else {
                players.get(o).setHp(players.get(o).getHp() - x);
            }
            int mjt=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    mjt+=1;
                }
            }
            int up=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    player.mafia m = new player.mafia(players.get(i + 1).getHp(), 1, "Mafia");
                    if (players.get(i+1).getHp()-(tragethp / mjt) >= 0) {
                        m.mafiapower(mjt, tragethp);
                        players.get(i+1).setHp(m.getHp());
                        if(m.getHp()>up){
                            m.setHp(players.get(i+1).getHp()-up);
                        }
                    }
                    else{
                        up+=players.get(i+1).getHp()-(tragethp/mjt);
                        players.get(i+1).setHp(0);
                    }
                }
            }int towardvote = 0;
            int io;

            System.out.println("Detectives choose a player to test.");
            Random ap = new Random();
            io = ap.nextInt(n-1)+1;
            while (players.get(io).getType().equals("Detector") || players.get(io).getLivestatus() == 0) {
                io = ap.nextInt(n-1)+1;
            }
            if (players.get(io).getType().equals("Mafia")){
                players.get(io).setLivestatus(0);
                t+=1;
            }
            int someone;
            if (players.get(userid).getLivestatus()==1) {
                System.out.println("Healer choose a player to heal:");
                someone = sc.nextInt();
                if (players.get(someone).getLivestatus()==0){
                    players.get(someone).setLivestatus(1);
                }
                while (players.get(someone).getLivestatus() == 0) {
                    someone = sc.nextInt();
                }
            }
            else {
                int ui=0;
                for (int i=0;i<n;i++){
                    if (players.get(i+1).getLivestatus()==1&&players.get(i+1).getType().equals("Healer"))
                    {
                        ui=1;
                    }
                }
                if(ui==1) {

                    System.out.println("Healers have chosen someone to heal.");
                    Random healer = new Random();
                    someone = healer.nextInt(n - 1) + 1;
                    while (players.get(someone).getLivestatus() == 0) {
                        someone = healer.nextInt(n - 1) + 1;
                    }
                    System.out.println(someone);
                }
                else{
                    System.out.println("NO Healer is alive");
                    someone=1;
                }
            }
            int iu=players.get(someone).getHp();
            players.get(someone).setHp(iu+500);
            players.get(someone).setLivestatus(1);

            int iup=players.get(someone).getHp();
            players.get(someone).setHp(iup+500);
            System.out.println("--End of actions--");
            if(players.get(o).getLivestatus()==0){
                System.out.println("Player"+o +" has died.");
                y+=1;
            }
            voting(n,userid);
            System.out.println("--End of round "+r+"--");
        }
        else {

            int o;
            System.out.println();
            System.out.println("Mafias has choosen their target");
            Random rp=new Random();
            int nj= rp.nextInt(n-1)+1;
            while (players.get(nj).getType().equals("Mafia")||players.get(nj).getLivestatus()==0){
                nj= rp.nextInt(n-1)+1;
            }
            o=nj;
            int x = 0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")) {
                    x += players.get(i + 1).getHp();
                }
            }
            int tragethp=players.get(o).getHp();
            if (players.get(o).getHp() - x < 0) {
                players.get(o).setHp(0);
                players.get(o).setLivestatus(0);
            } else {
                players.get(o).setHp(players.get(o).getHp() - x);
            }
            int mjt=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    mjt+=1;
                }
            }
            int up=0;
            for (int i = 0; i < n; i++) {
                if (players.get(i + 1).getType().equals("Mafia")&&players.get(i+1).getHp()>0) {
                    player.mafia m = new player.mafia(players.get(i + 1).getHp(), 1, "Mafia");
                    if (players.get(i+1).getHp()-(tragethp / mjt) >= 0) {
                        m.mafiapower(mjt, tragethp);
                        players.get(i+1).setHp(m.getHp());
                        if(m.getHp()>up){
                            m.setHp(players.get(i+1).getHp()-up);
                        }
                    }
                    else{
                        up+=players.get(i+1).getHp()-(tragethp/mjt);
                        players.get(i+1).setHp(0);
                    }
                }
            }int towardvote = 0;
            int io;
            System.out.println("Detectives choose a player to test.");
            Random ap = new Random();
            io = ap.nextInt(n-1)+1;
            while (players.get(io).getType().equals("Detector") || players.get(io).getLivestatus() == 0) {
                io = ap.nextInt(n-1)+1;
            }

            if (players.get(io).getType().equals("Mafia")){
                players.get(io).setLivestatus(0);
                t+=1;
            }
            System.out.println("Healers have chosen someone to heal.");
            Random healer=new Random();
            int someone= healer.nextInt(n - 1) + 1;
            while (players.get(someone).getLivestatus()==0) {
                someone= healer.nextInt(n - 1) + 1;
            }
            if(someone==o){
                int iu=players.get(someone).getHp();
                players.get(someone).setHp(iu+500);
                players.get(someone).setLivestatus(1);
            }
            int iu=players.get(someone).getHp();
            players.get(someone).setHp(iu+500);
            System.out.println("--End of actions--");
            if(players.get(o).getLivestatus()==0){
                System.out.println("Player"+o +" has died.");
                y+=1;
            }
            voting(n,userid);
            System.out.println("--End of round "+r+"--");
        }
    }
    public void voting(int n,int userid){
        Scanner in=new Scanner(System.in);
        int jo = 0;
        if(players.get(userid).getLivestatus()!=0) {
            System.out.println("Select a person to vote out:");
            jo = in.nextInt();
        }
        else{
            Random h=new Random();
            int hh= h.nextInt(n-1)+1;
            while (players.get(hh).getLivestatus()==0) {

                hh=h.nextInt(n-1)+1;
            }

        }
        int arr[]=new int[n];
        arr[0]=jo;
        Random rn=new Random();
        for(int i=1;i<n;i++){
            int r= rn.nextInt(n - 1)+1;
            if(players.get(r).getLivestatus()==0){
                while (players.get(r).getLivestatus()==0){
                    r= rn.nextInt(n - 1)+1;
                }
            }
            arr[i]=r;
        }
        int m=mostFrequent(arr,n);
        players.get(m).setLivestatus(0);
        System.out.println("Player"+m+" has been voted out.");
        if (players.get(m).getType().equals("Mafia")) {
            t += 1;
        }
        else{
            y+=1;
        }
    }
    static int mostFrequent(int arr[], int n)
    {
        Arrays.sort(arr);
        int max_count = 1, res = arr[0];
        int curr_count = 1;
        for (int i = 1; i < n; i++)
        {
            if (arr[i] == arr[i - 1])
                curr_count++;
            else
            {
                if (curr_count > max_count)
                {
                    max_count = curr_count;
                    res = arr[i - 1];
                }
                curr_count = 1;
            }
        }
        if (curr_count > max_count)
        {
            max_count = curr_count;
            res = arr[n - 1];
        }

        return res;
    }
    public void gameover(int po,int alivecount,int n){
        System.out.println("Game Over.");
        if(po*2>alivecount){
            System.out.println("The Mafias have won.");
        }
        else{
            System.out.println("The Mafias have lost.");
        }

        System.out.println("Mafias are ");
        for(int i=0;i<n;i++){
            if(players.get(i+1).getType().equals("Mafia")){
                System.out.print("Player "+(i+1)+" ");
            }
        }
        System.out.println();
        System.out.println("Detective are ");
        for(int i=0;i<n;i++){
            if(players.get(i+1).getType().equals("Detector")){
                System.out.print("Player "+(i+1)+" ");
            }
        }
        System.out.println();
        System.out.println("Healer are ");
        for(int i=0;i<n;i++){
            if(players.get(i+1).getType().equals("Healer")){
                System.out.print("Player "+(i+1)+" ");
            }
        }
        System.out.println();
        System.out.println("Commmon are ");
        for(int i=0;i<n;i++){
            if(players.get(i+1).getType().equals("Common")){
                System.out.print("Player "+(i+1)+" ");
            }
        }
    }
    public void enterasmafia(int n) {
        int u = 0;
        int arr[]=new int[n/5];
        int o=0;
        int asamafiauserid=0;
        for (int i = 0; i < n; i++) {
            if (players.get(i+1).getType().equals("Mafia") && u == 0) {
                System.out.println("You are Player" + (i+1));
                asamafiauserid=i+1;
                System.out.println("You are a mafia.");
                arr[o]=i+1;
                o=o+1;
                u = 1;

            } else if (players.get(i+1).getType().equals("Mafia")) {
                System.out.println("Other Mafia are Player " + (i+1));
                arr[o]=i+1;
                o+=1;
            }
        }
        int r=1;
        int p=0;
        int po=0;
        for(int i=0;i<n/5;i++){
            if(players.get(arr[i]).getLivestatus()==1){
                p=1;
                po+=1;
            }
        }
        int alivecount=0;
        for(int i=0;i<n;i++){
            if(players.get(i+1).getLivestatus()==1&&(players.get(i+1).getType().equals("Detector")||players.get(i+1).getType().equals("Common")||players.get(i+1).getType().equals("Healer"))){
                alivecount+=1;
            }
        }
        int a=1;
        while (p==1&&alivecount>=2*po) {
            if (p == 1 && alivecount >= 2 * po && players.get(asamafiauserid).getLivestatus() != 0) {
                round(n, r, a, asamafiauserid);
                if(y>=1){
                    alivecount -= y;
                    y=0;
                }

                if (t >= 1) {
                    po -= t;
                    t = 0;
                }
                if (po == 0) {
                    p = 0;
                }
                r += 1;
            } else if (p == 1 && alivecount >= 2 * po) {
                round(n, r, a, asamafiauserid);

                if(y>=1){
                    alivecount -= y;
                    y=0;
                }

                if (t >= 1) {
                    po -= t;
                    t = 0;
                }
                if (po == 0) {
                    p = 0;
                }

                r += 1;
            }
            else {
                break;
            }
        }

        gameover(po,alivecount,n);
    }
    public void enterasdetector(int n) {
        int u = 0;
        int arr[]=new int[n/5];
        int o=0;
        int asadetectoruserid=0;
        for (int i = 0; i < n; i++) {
            if (players.get(i+1).getType().equals("Detector") && u == 0) {
                System.out.println("You are Player" + (i + 1));
                System.out.println("You are a Detector.");
                u = 1;
                asadetectoruserid=i+1;
            } else if (players.get(i+1).getType().equals("Detector")) {
                System.out.println("Other Detector are Player " + (i + 1));
            }
        }

        int r=1;
        int p=0;
        int po=0;
        for(int i=0;i<n;i++){
            if(players.get(i+1).getLivestatus()==1&&players.get(i+1).getType().equals("Mafia")){
                p=1;
                po+=1;
            }
        }
        int alivecount=0;
        for(int i=0;i<n;i++){
            if(players.get(i+1).getLivestatus()==1&&(players.get(i+1).getType().equals("Detector")||players.get(i+1).getType().equals("Common")||players.get(i+1).getType().equals("Healer"))){
                alivecount+=1;
            }
        }
        int a=2;
        while (p==1&&alivecount>=2*po) {
            if (p == 1 && alivecount >= 2 * po) {
                round(n, r, a, asadetectoruserid);

                if(y>=1){
                    alivecount -= y;
                    y=0;
                }

                if (t >= 1) {
                    po -= t;
                    t = 0;
                }
                if (po == 0) {
                    p = 0;
                }

                r += 1;
            }
            else {
                break;
            }
        }

        gameover(po,alivecount,n);


    }
    public void enterashealer(int n) {
        int u = 0;
        int o=0;
        int asahealeruserid=0;
        for (int i = 0; i < n; i++) {
            if (players.get(i+1).getType().equals("Healer") && u == 0) {
                System.out.println("You are Player" + (i + 1));
                System.out.println("You are a Healer.");
                asahealeruserid=i+1;
                u = 1;
            } else if (players.get(i+1).getType().equals("Healer")) {
                System.out.println("Other Healer are Player " + (i + 1));
            }
        }
        int r=1;
        int p=0;
        int po=0;
        for(int i=0;i<n;i++){
            if(players.get(i+1).getLivestatus()==1&&players.get(i+1).getType().equals("Mafia")){
                p=1;
                po+=1;
            }
        }
        int alivecount=0;
        for(int i=0;i<n;i++){
            if(players.get(i+1).getLivestatus()==1&&(players.get(i+1).getType().equals("Detector")||players.get(i+1).getType().equals("Common")||players.get(i+1).getType().equals("Healer"))){
                alivecount+=1;
            }
        }
        int a=3;
        while (p==1&&alivecount>=2*po) {
            if (p == 1 && alivecount >= 2 * po) {
                round(n, r, a, asahealeruserid);
                if(y>=1){
                    alivecount -= y;
                    y=0;
                }
                if (t >= 1) {
                    po -= t;
                    t = 0;
                }
                if (po == 0) {
                    p = 0;
                }

                r += 1;
            }
            else {
                break;
            }
        }

        gameover(po,alivecount,n);


    }
    public void enterascommon(int n) {
        int u = 0;
        int arr[]=new int[n/5];
        int o=0;
        int asacommonuserid=0;

        for (int i = 0; i < n; i++) {
            if (players.get(i+1).getType().equals("Common") && u == 0) {
                System.out.println("You are Common" + (i + 1));
                System.out.println("You are a Common.");
                asacommonuserid=i+1;
                u = 1;
            }
        }
        int r=1;
        int p=0;
        int po=0;
        for(int i=0;i<n;i++){
            if(players.get(i+1).getLivestatus()==1&&players.get(i+1).getType().equals("Mafia")){
                p=1;
                po+=1;
            }
        }
        int alivecount=0;
        for(int i=0;i<n;i++){
            if(players.get(i+1).getLivestatus()==1&&(players.get(i+1).getType().equals("Detector")||players.get(i+1).getType().equals("Common")||players.get(i+1).getType().equals("Healer"))){
                alivecount+=1;
            }
        }
        int a=4;
        while (p==1&&alivecount>=2*po) {
            if (p == 1 && alivecount >= 2 * po) {
                round(n, r, a, asacommonuserid);

                if(y>=1){
                    alivecount -= y;
                    y=0;
                }

                if (t >= 1) {
                    po -= t;
                    t = 0;
                }
                if (po == 0) {
                    p = 0;
                }

                r += 1;
            }
            else {
                break;
            }
        }

        gameover(po,alivecount,n);

    }

    public void enterasanyone(int n) throws Exception {
        int r = new Random().nextInt(4);
        switch (r) {
            case 0:
                enterasdetector(n);
                break;
            case 1:
                enterasmafia(n);
                break;
            case 2:
                enterascommon(n);
                break;
            case 3:
                enterashealer(n);
                break;

            default:
                throw new Exception("ThankYou(" + n + "): unsupported value!");

        }
    }
}
public class game {
    public static void main(String args[]) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Mafia");
        System.out.println("Enter Number of players:");

        while (input.hasNext()) {

            int n = input.nextInt();
            while (n<6){
                n=input.nextInt();
            }
            gameM ma=new gameM(n);
            System.out.println("Choose a Character\n" +
                    "1) Mafia\n" +
                    "2) Detective\n" +
                    "3) Healer\n" +
                    "4) Commoner\n" +
                    "5) Assign Randomly");
            int t=input.nextInt();
            if(t==1){
                ma.enterasmafia(n);
            }
            else if(t==2){
                ma.enterasdetector(n);
            }
            else if(t==3){
                ma.enterashealer(n);
            }
            else if(t==4){
                ma.enterascommon(n);
            }
            else{
                ma.enterasanyone(n);
            }
        }
    }
}