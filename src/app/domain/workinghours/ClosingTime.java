package app.domain.workinghours;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.Time;

/**
 * 終業時刻
 */
public final class ClosingTime {

    /**
     * 終業時刻の時間
     */
    protected final LocalTime time;

    /**
     * コンストラクタ
     * 
     * @param time 終業時刻の時間
     */
    private ClosingTime(final LocalTime time) {
        this.time = time;
    }

    /**
     * 終業時の打刻のときに呼び出す用
     * 
     * @return 終業時刻
     */
    public static ClosingTime record() {
        return new ClosingTime(LocalTime.now(ZoneId.of("Asia/Tokyo")));
    }

    /**
     * インフラストラクチャ層の
     * リポジトリなどから終業時刻を復元するときに呼び出す用
     * 
     * @param time 時刻
     * @return 終業時刻
     */
    public static ClosingTime reconstruct(Time time) {
        LocalTime localTime = time.toLocalTime();
        return new ClosingTime(localTime);
    }

    /**
     * 終業時刻の時間を返します
     * 
     * @return 終業時刻の時間
     */
    public LocalTime getValue() {
        return this.time;
    }

    /**
     * プレゼンテーション層の
     * ビューで終業時刻を表示するときに呼び出す用
     * 
     * 例) 17時     → 17:00
     *     17時30分 → 17:30
     * 
     * @return 時刻フォーマット(HH:mm)の文字列
     */
    public String toString() {

        final DateTimeFormatter pattern
            = DateTimeFormatter.ofPattern("HH:mm");

        return this.time.format(pattern);
    }

}
