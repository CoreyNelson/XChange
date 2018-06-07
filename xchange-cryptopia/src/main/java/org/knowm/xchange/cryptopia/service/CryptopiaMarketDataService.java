package org.knowm.xchange.cryptopia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.CryptopiaAdapters;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.utils.Assert;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CryptopiaMarketDataService extends CryptopiaMarketDataServiceRaw
        implements MarketDataService {

    public CryptopiaMarketDataService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
        if (args != null && args.length > 0) {
            long hours = (long) args[0];

            return CryptopiaAdapters.adaptTicker(getCryptopiaTicker(currencyPair, hours));
        }

        return CryptopiaAdapters.adaptTicker(getCryptopiaTicker(currencyPair));
    }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    final List<CurrencyPair> currencyPairs = new ArrayList<>();
    if (params instanceof CurrencyPairsParam) {
      currencyPairs.addAll(((CurrencyPairsParam) params).getCurrencyPairs());
    }
    return getCryptopiaMarkets()
        .stream()
        .map(
            cryptopiaTicker ->
                CryptopiaAdapters.adaptTicker(
                    cryptopiaTicker))
        .filter(
            ticker -> currencyPairs.size() == 0 || currencyPairs.contains(ticker.getCurrencyPair()))
        .collect(Collectors.toList());
    }

    @Override
    public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
        if (args != null && args.length > 0) {
            long orderCount = (long) args[0];

            return CryptopiaAdapters.adaptOrderBook(
                    getCryptopiaOrderBook(currencyPair, orderCount), currencyPair);
        }

        return CryptopiaAdapters.adaptOrderBook(getCryptopiaOrderBook(currencyPair), currencyPair);
    }

    @Override
    public List<OrderBook> getOrderBooks(List<? extends CurrencyPair> pairs, int depth) throws IOException {
        List<CryptopiaOrderBook> cobs = getCryptopiaOrderBooks(pairs, depth);
        Assert.isTrue(pairs.size() == cobs.size(), "The OrderBook list should be the same size as the CurrencyPair list.");

        List<OrderBook> obs = new ArrayList<>();
        for (int i = 0; i < cobs.size(); i++) {
            obs.add(CryptopiaAdapters.adaptOrderBook(cobs.get(i), pairs.get(i)));
        }
        return obs;
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
        if (args != null && args.length > 0) {
            long hours = (long) args[0];

            return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair, hours));
        }

        return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair));
    }
}
