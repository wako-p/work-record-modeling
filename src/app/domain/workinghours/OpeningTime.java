package app.domain.workinghours;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.Time;

/**
 * 始業時刻
 */
public final class OpeningTime {

    /**
     * 始業時刻の時間
     */
    protected final LocalTime time;

    /**
     * コンストラクタ
     * 
     * @param time 始業時刻の時間
     */
    private OpeningTime(final LocalTime time) {
        this.time = time;
    }

    /**
     * 始業時の打刻のときに呼び出す用
     * 
     * @return 始業時刻
     */
    public static OpeningTime record() {
        return new OpeningTime(LocalTime.now(ZoneId.of("Asia/Tokyo")));
    }

    /**
     * インフラストラクチャ層の
     * リポジトリなどから始業時刻を復元するときに呼び出す用
     * 
     * @param time 時刻
     * @return 始業時刻
     */
    public static OpeningTime reconstruct(Time time) {
        LocalTime localTime = time.toLocalTime();
        return new OpeningTime(localTime);
    }

    /**
     * 始業時刻の時間を返します
     * 
     * @return 始業時刻の時間
     */
    public LocalTime getValue() {
        return this.time;
    }

    /**
     * プレゼンテーション層の
     * ビューで始業時刻を表示するときに呼び出す用
     * 
     * 例) 9時     → 09:00
     *     9時30分 → 09:30
     * 
     * @return 時刻フォーマット(HH:mm)の文字列
     */
    public String toString() {

        final DateTimeFormatter pattern
            = DateTimeFormatter.ofPattern("HH:mm");

        return this.time.format(pattern);
    }

}
