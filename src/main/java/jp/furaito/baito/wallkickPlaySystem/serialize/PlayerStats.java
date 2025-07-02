package jp.furaito.baito.wallkickPlaySystem.serialize;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats implements ConfigurationSerializable {
    //各値を保存するキー
    private static final String KILL = "kill";                  //キル数
    private static final String DEATH = "death";                //デス数
    private static final String WIN = "win";                    //勝利数
    private static final String LOSE = "lose";                  //敗北数
    private static final String PLAY_STANDARD = "playStandard"; //通常モードプレイ数
    private static final String PLAY_UNIQUE = "playUnique";     //特殊モードプレイ数

    //初期値0
    public int kill = 0;
    public int death = 0;
    public int win = 0;
    public int lose = 0;
    public int playStandard = 0;
    public int playUnique = 0;

    //コンストラクタ
    public PlayerStats(){
    }

    //各値のgetとset
    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getPlayStandard() {
        return playStandard;
    }

    public void setPlayStandard(int playStandard) {
        this.playStandard = playStandard;
    }

    public int getPlayUnique() {
        return playUnique;
    }

    public void setPlayUnique(int playUnique) {
        this.playUnique = playUnique;
    }

    /**
     * 情報をシリアライズする
     *
     * @return シリアライズされた情報
     */
    public Map<String,Object> serialize(){

        // マップを作る
        Map<String,Object> map=new HashMap<String,Object>();

        // 情報を「キー」と「値」のペアにして書き出す
        map.put(KILL,kill);
        map.put(DEATH,death);
        map.put(WIN,win);
        map.put(LOSE,lose);
        map.put(PLAY_STANDARD,playStandard);
        map.put(PLAY_UNIQUE,playUnique);

        Bukkit.broadcastMessage("test: "+map);

        // データを保存したマップを返す（このマップの中身がconfig.ymlなどに書かれる）
        return map;
    }

    /**
     * 情報をデシリアライズする
     *
     * @param map
     *            シリアライズされた情報
     * @return デシリアライズされた情報
     */
    public static PlayerStats deserialize(Map<String,Object> map){

        // config.ymlから読み込まれたマップが引数に渡されるので、このデータを元に情報を作成（復元）する

        //オブジェクトを作る
        PlayerStats subjugation=new PlayerStats();

        //「キー」で「値」を取り出してオブジェクトにセット
        subjugation.setKill(Integer.parseInt(map.get(KILL).toString()));
        subjugation.setDeath(Integer.parseInt(map.get(DEATH).toString()));
        subjugation.setWin(Integer.parseInt(map.get(WIN).toString()));
        subjugation.setLose(Integer.parseInt(map.get(LOSE).toString()));
        subjugation.setPlayStandard(Integer.parseInt(map.get(PLAY_STANDARD).toString()));
        subjugation.setPlayUnique(Integer.parseInt(map.get(PLAY_UNIQUE).toString()));

        //オブジェクトを返す
        return subjugation;
    }
}

//95%お借りしました
//https://www.jias.jp/minecraft/20141101.htm
