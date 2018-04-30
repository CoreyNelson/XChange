package org.knowm.xchange.cryptopia.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.exceptions.ExchangeException;

public class CryptopiaMarketDataServiceRaw extends CryptopiaBaseService {

    public CryptopiaMarketDataServiceRaw(Exchange exchange) {

        super(exchange);
    }

    public List<CryptopiaCurrency> getCryptopiaCurrencies() throws IOException {
        CryptopiaBaseResponse<List<CryptopiaCurrency>> response = cryptopia.getCurrencies();

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public List<CryptopiaTradePair> getCryptopiaTradePairs() throws IOException {
        CryptopiaBaseResponse<List<CryptopiaTradePair>> response = cryptopia.getTradePairs();

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public List<CryptopiaTicker> getCryptopiaMarkets() throws IOException {
        CryptopiaBaseResponse<List<CryptopiaTicker>> response = cryptopia.getMarkets();

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public List<CryptopiaTicker> getCryptopiaMarkets(Currency baseMarket) throws IOException {
        CryptopiaBaseResponse<List<CryptopiaTicker>> response =
                cryptopia.getMarkets(baseMarket.getCurrencyCode());

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public List<CryptopiaTicker> getCryptopiaMarkets(Currency baseMarket, long hours)
            throws IOException {
        CryptopiaBaseResponse<List<CryptopiaTicker>> response =
                cryptopia.getMarkets(baseMarket.getCurrencyCode(), hours);

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public CryptopiaTicker getCryptopiaTicker(CurrencyPair market) throws IOException {
        CryptopiaBaseResponse<CryptopiaTicker> response = cryptopia.getMarket(getPairString(market));

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public CryptopiaTicker getCryptopiaTicker(CurrencyPair market, long hours) throws IOException {
        CryptopiaBaseResponse<CryptopiaTicker> response = cryptopia.getMarket(getPairString(market), hours);

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public List<CryptopiaMarketHistory> getCryptopiaTrades(CurrencyPair market) throws IOException {
        CryptopiaBaseResponse<List<CryptopiaMarketHistory>> response =
                cryptopia.getMarketHistory(getPairString(market));

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public List<CryptopiaMarketHistory> getCryptopiaTrades(CurrencyPair market, long hours)
            throws IOException {
        CryptopiaBaseResponse<List<CryptopiaMarketHistory>> response =
                cryptopia.getMarketHistory(getPairString(market), hours);

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public CryptopiaOrderBook getCryptopiaOrderBook(CurrencyPair market) throws IOException {
        CryptopiaBaseResponse<CryptopiaOrderBook> response = cryptopia.getMarketOrders(getPairString(market));

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public CryptopiaOrderBook getCryptopiaOrderBook(CurrencyPair market, long orderCount)
            throws IOException {
        CryptopiaBaseResponse<CryptopiaOrderBook> response =
                cryptopia.getMarketOrders(getPairString(market), orderCount);

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    public List<CryptopiaOrderBook> getCryptopiaOrderBooks(List<? extends CurrencyPair> pairs, int orderCount)
            throws IOException {
        CryptopiaBaseResponse<List<CryptopiaOrderBook>> response =
                cryptopia.getMarketOrderGroups(getPairsString(pairs), orderCount);

        if (response.getError() != null) {
            throw new ExchangeException(response.getError());
        }

        return response.getData();
    }

    private String getPairsString(List<? extends CurrencyPair> pairs) {
        return pairs.stream()
                .map(pair -> getPairString(pair))
                .collect(Collectors.joining("-"));
    }

    private String getPairString(CurrencyPair pair) {
        return pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode();
    }
}
