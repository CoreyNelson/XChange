package org.knowm.xchange.cryptopia.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptopia.CryptopiaAdapters;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        return getCryptopiaMarkets().stream()
                .map(CryptopiaAdapters::adaptTicker)
                .collect(Collectors.toList());
    }

    private CryptopiaExchange getCryptopiaExchange() {
        return (CryptopiaExchange) exchange;
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
    public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
        if (args != null && args.length > 0) {
            long hours = (long) args[0];

            return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair, hours));
        }

        return CryptopiaAdapters.adaptTrades(getCryptopiaTrades(currencyPair));
    }
}
