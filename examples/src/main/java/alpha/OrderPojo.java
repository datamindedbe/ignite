package alpha;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.math.BigDecimal;

public class OrderPojo {
    public static class Key {
        public String objUID;
        public boolean farLeg;

        public Key(String objUID, boolean farLeg) {
            this.objUID = objUID;
            this.farLeg = farLeg;
        }
    }

    @QuerySqlField(name = "OBJ_UID")
    public String objUID;

    @QuerySqlField(name = "FAR_LEG")
    public boolean farLeg;

    @QuerySqlField(name = "DEAL_TYPE")
    public int dealType;

    @QuerySqlField(name = "BUYER_LEGAL_ENTITY", index = true)
    public long buyerLegalEntity;

    @QuerySqlField(name = "SELLER_LEGAL_ENTITY", index = true)
    public long sellerLegalEntity;

    @QuerySqlField(name = "TRADER")
    @AffinityKeyMapped
    public String trader;

    @QuerySqlField(name = "CURRENCY1", index = true)
    public String currency1;

    @QuerySqlField(name = "CUR1_AMOUNT")
    public long cur1Amount;

    @QuerySqlField(name = "CURRENCY2", index = true)
    public String currency2;

    @QuerySqlField(name = "CUR2_AMOUNT")
    public long cur2Amount;
}
