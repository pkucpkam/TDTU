import yfinance as yf
import pandas as pd
import pandas_datareader.data as web
from datetime import datetime

tickers = ['AAPL', 'MSFT', 'GOOGL', 'AMZN', 'META']
data = yf.download(tickers, start='2018-01-01', end='2023-12-31', group_by='ticker', auto_adjust=True)

close_prices = pd.DataFrame()
for ticker in tickers:
    close_prices[ticker] = data[ticker]['Close']
    close_prices[f'{ticker}_Open'] = data[ticker]['Open']
    close_prices[f'{ticker}_Volume'] = data[ticker]['Volume']
    close_prices[f'{ticker}_High'] = data[ticker]['High']
    close_prices[f'{ticker}_Low'] = data[ticker]['Low']

close_prices.reset_index(inplace=True)
close_prices['Year'] = close_prices['Date'].dt.year
close_prices['Month'] = close_prices['Date'].dt.month
close_prices['Day'] = close_prices['Date'].dt.day
close_prices['DayOfWeek'] = close_prices['Date'].dt.dayofweek

# Lấy dữ liệu vĩ mô từ FRED
gdp_data = web.DataReader('GDP', 'fred', start='2017-12-01', end='2023-12-31').reset_index()
gdp_data.rename(columns={'DATE': 'Date'}, inplace=True)
gdp_data['Date'] = pd.to_datetime(gdp_data['Date'])

cpi_data = web.DataReader('CPIAUCSL', 'fred', start='2017-12-01', end='2023-12-31').reset_index()
cpi_data.rename(columns={'DATE': 'Date'}, inplace=True)
cpi_data['Date'] = pd.to_datetime(cpi_data['Date'])

dpi_data = web.DataReader('DSPIC96', 'fred', start='2017-12-01', end='2023-12-31').reset_index()
dpi_data.rename(columns={'DATE': 'Date'}, inplace=True)
dpi_data['Date'] = pd.to_datetime(dpi_data['Date'])

# Lấy dữ liệu Lợi suất trái phiếu kỳ hạn 10 năm (GS10)
bond_yield_data = web.DataReader('GS10', 'fred', start='2017-12-01', end='2023-12-31').reset_index()
bond_yield_data.rename(columns={'DATE': 'Date'}, inplace=True)
bond_yield_data['Date'] = pd.to_datetime(bond_yield_data['Date'])

def merge_macro(df_stock, df_macro, col_name):
    return pd.merge_asof(df_stock.sort_values('Date'),
                         df_macro.sort_values('Date'),
                         on='Date',
                         direction='backward')[col_name]

close_prices['GDP'] = merge_macro(close_prices, gdp_data, 'GDP')
close_prices['CPI'] = merge_macro(close_prices, cpi_data, 'CPIAUCSL')
close_prices['DPI'] = merge_macro(close_prices, dpi_data, 'DSPIC96')
close_prices['Bond_Yield_10Y'] = merge_macro(close_prices, bond_yield_data, 'GS10')

# Xử lý missing values
close_prices = close_prices.ffill().bfill()

# In thông tin
print("Columns in close_prices:\n", close_prices.columns.tolist())
print("Sample of macroeconomic and financial columns:\n",
      close_prices[['Date', 'GDP', 'CPI', 'DPI', 'Bond_Yield_10Y']].head(10))

# Hiển thị bảng
pd.set_option('display.max_columns', None)
pd.set_option('display.width', 1000)

# Lưu dữ liệu vào file CSV
close_prices.to_csv("financial_data.csv", index=False)
print("Đã lưu dữ liệu vào financial_macro_data_with_bond_yield.csv")
