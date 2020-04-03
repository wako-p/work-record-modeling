package app.domain.workinghours;

import java.math.BigDecimal;

/**
 * 遠隔地調整時間
 */
public final class RemoteAdjustmentHours {

    /**
     * 遠隔地調整時間
     */
    private final BigDecimal hours;

    /**
     * コンストラクタ
     * 
     * @param hours 遠隔地調整時間
     */
    private RemoteAdjustmentHours(final BigDecimal hours) {
        this.hours = hours;
    }

    /**
     * 有効な遠隔地調整時間かを返します
     * 
     * @return 有効な場合はtrue
     */
    private static boolean isValid(final double hours) {
        // TODO : 仕様がわからないので0～8時間までにしちゃう
        return (0.00 <= hours) && (hours <= 8.00);
    }

    /**
     * インフラストラクチャ層の
     * リポジトリなどから遠隔地調整時間を復元するときに呼び出す用
     * 
     * @param hours 遠隔地調整時間の値
     * @return 遠隔地調整時間
     */
    public static RemoteAdjustmentHours reconstruct(final double hours) {

        if (!RemoteAdjustmentHours.isValid(hours)) {
            throw new IllegalArgumentException();
        }
        return new RemoteAdjustmentHours(BigDecimal.valueOf(hours));
    }

    /**
     * 遠隔地調整時間を返します
     * 
     * 例) 1時間     → 1
     *     1時間30分 → 1.5
     * 
     * @return 時間の数値フォーマットの数値
     */
    public BigDecimal getValue() {
        return this.hours;
    }

    /**
     * プレゼンテーション層の
     * ビューで遠隔地調整時間を表示するときに呼び出す用
     * 
     * 例) 1時間     → 1.00
     *     1時間30分 → 1.50
     * 
     * @return 時間の数値フォーマットの文字列
     */
    public String toString() {
        return String.format("%.2f", this.hours.doubleValue());
    }

}
