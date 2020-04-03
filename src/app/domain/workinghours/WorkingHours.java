package app.domain.workinghours;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

/**
 * 在社時間
 */
public final class WorkingHours {

    /**
     * 在社時間
     * (終業時刻 - 始業時刻) + 遠隔地調整時間
     */
    private final BigDecimal hours;

    /**
     * コンストラクタ
     * 
     * @param opening           始業時刻
     * @param closing           終業時刻
     * @param remoteAdjustment  遠隔地調整時間
     */
    private WorkingHours(
        final OpeningTime           opening,
        final ClosingTime           closing,
        final RemoteAdjustmentHours remoteAdjustment
    ) {

        // 
        // 在社時間に関連する業務ルールが全て在社時間クラスに書かれている
        // 
        // ・始業時刻 <= 終業時刻じゃないとダメとか
        // ・在社時間は、終業時刻 - 始業時刻で求められるだとか
        // ・在社時間は、遠隔地調整時間が加算されるだとか
        // 
        // つまり、在社時間について知りたい場合は
        // ドキュメントを見なくても在社時間クラスを見れば業務ルールを把握できる
        // (=ソースコードが、クラスが、業務ルールを表現している)
        // 

        if (!WorkingHours.isValid(opening, closing)) {
            throw new IllegalArgumentException();
        }

        // 在社時間は始業時間と終業時間の差分で求められる
        final BigDecimal minutes = BigDecimal.valueOf(
            ChronoUnit.MINUTES.between(opening.getValue(), closing.getValue()));

        // 在社時間を分から時間に換算する
        final BigDecimal hours = minutes.divide(BigDecimal.valueOf(60));

        // 在社時間に遠隔地調整時間を加算する
        this.hours = hours.add(remoteAdjustment.getValue());
    }

    private WorkingHours(BigDecimal hours)
    {
        this.hours = hours;
    }

    public static WorkingHours from(OpeningTime opening, ClosingTime closing, RemoteAdjustmentHours remoteAdjustment) {
        return new WorkingHours(opening, closing, remoteAdjustment);
    }

    public static WorkingHours reconstruct(BigDecimal hours) {
        return new WorkingHours(hours);
    }

    /**
     * 有効な始業時刻と終業時刻かを返します
     * 
     * @param opening 始業時刻
     * @param closing 終業時刻
     * @return 有効な場合はtrue
     */
    private static boolean isValid(
        final OpeningTime opening,
        final ClosingTime closing
    ) {
        // 始業時刻 <= 終業時刻の場合は正の値が返るのでtrue
        // 始業時刻 >  終業時刻の場合は負の値が返るのでfalse
        return 0 <= ChronoUnit.MINUTES.between(opening.getValue(), closing.getValue());
    }

    /**
     * 在社時間を返します
     * 
     * 例) 6時間     → 6
     *     8時間45分 → 8.75
     * 
     * @return 時間の数値フォーマットの数値
     */
    public BigDecimal getValue() {
        return this.hours;
    }

    public WorkingHours add(WorkingHours other) {
        return new WorkingHours(this.hours.add(other.hours));
    }

    /**
     * プレゼンテーション層の
     * ビューで在社時間を表示するときに呼び出す用
     * 
     * 例) 6時間     → 6.00
     *     8時間45分 → 8.75
     * 
     * @return 時間の数値フォーマットの文字列
     */
    public String toString() {
        return String.format("%.2f", this.hours.doubleValue());
    }

}
