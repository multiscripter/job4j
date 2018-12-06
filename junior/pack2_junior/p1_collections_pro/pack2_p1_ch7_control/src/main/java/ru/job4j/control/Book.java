package ru.job4j.control;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
/**
 * Класс Book реализует сущность Книга с заказами.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 4
 * @since 2017-07-02
 */
class Book {
    /**
     * Название книги заказов.
     */
    private String name;
    /**
     * Заказы на покупку. Отсортированы по id.
     */
    private HashMap<String, Order> buy;
    /**
     * Заказы на покупку. Отсортированы по убыванию цены.
     */
    private TreeSet<Order> buyPrice;
    /**
     * Заказы на продажу. Отсортированы по id.
     */
    private HashMap<String, Order> sell;
    /**
     * Заказы на продажу. Отсортированы по возрастанию цены.
     */
    private TreeSet<Order> sellPrice;
    /**
     * Конструктор.
     * @param name название книги.
     */
    Book(String name) {
        this.name = name;
        this.buy = new HashMap<>();
        this.buyPrice = new TreeSet<>(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o2.getPrice(), o1.getPrice());
            }
        });
        this.sell = new HashMap<>();
        this.sellPrice = new TreeSet<>(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
    }
    /**
     * Добавляет заказ (на покупку или продажу) на книгу.
     * @param orderId идентификатор заказа.
     * @param order заказ на покупку или продажу книги.
     */
    public void add(String orderId, Order order) {
        Order match = null;
        int volume = 0;
        int matchVolume = 0;
        int orderVolume = 0;
        if ("BUY".equals(order.getOperation())) {
            while ((match = this.searchMatch(this.sellPrice, order)) != null) {
                matchVolume = match.getVolume();
                orderVolume = order.getVolume();
                if (matchVolume != orderVolume) {
                    volume = Math.min(matchVolume, orderVolume);
                } else {
                    volume = matchVolume;
                }
                match.setVolume(matchVolume - volume);
                order.setVolume(orderVolume - volume);
                if (match.getVolume() == 0) {
                    this.sell.remove(match.getOrderId());
                    this.sellPrice.remove(match);
                } else {
                    this.sell.replace(match.getOrderId(), match);
                    this.sellPrice.add(match);
                }
                if (order.getVolume() == 0) {
                    break;
                }
            }
            if (order.getVolume() > 0) {
                this.buy.put(orderId, order);
                this.buyPrice.add(order);
            }
        } else if ("SELL".equals(order.getOperation())) {
            while ((match = this.searchMatch(this.buyPrice, order)) != null) {
                matchVolume = match.getVolume();
                orderVolume = order.getVolume();
                if (matchVolume != orderVolume) {
                    volume = Math.min(matchVolume, orderVolume);
                } else {
                    volume = matchVolume;
                }
                match.setVolume(matchVolume - volume);
                order.setVolume(orderVolume - volume);
                if (match.getVolume() == 0) {
                    this.buy.remove(match.getOrderId());
                    this.buyPrice.remove(match);
                } else {
                    this.buy.replace(match.getOrderId(), match);
                    this.buyPrice.add(match);
                }
                if (order.getVolume() == 0) {
                    break;
                }
            }
            if (order.getVolume() > 0) {
                this.sell.put(orderId, order);
                this.sellPrice.add(order);
            }
        }
    }
    /**
     * Возвращает строку со стаканом.
     * @return строка со стаканом.
     */
    public String gather() {
        StringBuilder str = new StringBuilder();
        Order bidCur = null;
        Order askCur = null;
        int bidVolume = 0;
        int askVolume = 0;
        double bidPrice = 0;
        double askPrice = 0;
        String placeholder = "-----------";
        String bidStr = null;
        String askStr = null;
        boolean bidAgr = false;
        boolean askAgr = false;
        //this.sort();
        Iterator<Order> iterBuy = this.buyPrice.iterator();
        Iterator<Order> iterSell = this.sellPrice.iterator();
        while (iterBuy.hasNext() || iterSell.hasNext()) {
            if (!askAgr) {
                if (iterBuy.hasNext()) {
                    bidCur = iterBuy.next();
                    //bidStr = String.format("%d@%.2f", bidCur.getVolume(), bidCur.getPrice());
                    if (bidVolume == 0) {
                        bidVolume = bidCur.getVolume();
                        bidPrice = bidCur.getPrice();
                    } else if (Double.compare(bidCur.getPrice(), bidPrice) == 0) {
                        bidVolume += bidCur.getVolume();
                        bidAgr = true;
                    } else {
                        bidStr = String.format("%d@%.2f", bidVolume, bidPrice);
                        bidVolume = bidCur.getVolume();
                        bidPrice = bidCur.getPrice();
                        bidAgr = false;
                    }
                } else {
                    bidStr = bidVolume == 0 ? placeholder : String.format("%d@%.2f", bidVolume, bidPrice);
                    bidVolume = 0;
                    bidAgr = false;
                }
            }
            if (!bidAgr) {
                if (iterSell.hasNext()) {
                    askCur = iterSell.next();
                    //askStr = String.format("%d@%.2f", askCur.getVolume(), askCur.getPrice());
                    if (askVolume == 0) {
                        askVolume = askCur.getVolume();
                        askPrice = askCur.getPrice();
                    } else if (Double.compare(askCur.getPrice(), askPrice) == 0) {
                        askVolume += askCur.getVolume();
                        askAgr = true;
                    } else {
                        askStr = String.format("%d@%.2f", askVolume, askPrice);
                        askVolume = askCur.getVolume();
                        askPrice = askCur.getPrice();
                        askAgr = false;
                    }
                } else {
                    askStr = askVolume == 0 ? placeholder : String.format("%d@%.2f", askVolume, askPrice);
                    askVolume = 0;
                    askAgr = false;
                }
            }
            if (!bidAgr && !askAgr && bidStr != null && askStr != null) {
                str.append(String.format("%-14s - %-14s\n", bidStr, askStr));
                bidStr = null;
                askStr = null;
                bidAgr = false;
                askAgr = false;
            }
        }
        if (bidVolume != 0 || askVolume != 0) {
            bidStr = bidVolume == 0 ? placeholder : String.format("%d@%.2f", bidVolume, bidPrice);
            askStr = askVolume == 0 ? placeholder : String.format("%d@%.2f", askVolume, askPrice);
            str.append(String.format("%-14s - %-14s\n", bidStr, askStr));
        }
        return str.toString();
    }
    /**
     * Получает название книги заказов.
     * @return название книги заказов.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Получает карту заказов на покупку книги.
     * @return карта заказов на покупку книги.
     */
    public HashMap<String, Order> getBuy() {
        return this.buy;
    }
    /**
     * Получает карту заказов на продажу книги.
     * @return карта заказов на продажу книги.
     */
    public HashMap<String, Order> getSell() {
        return this.sell;
    }
    /**
     * Удаляет заказ (на покупку, на продажу) из книги.
     * @param orderId идентификатор заказа.
     * @return true если заказ удалён. Иначе false.
     */
    public boolean remove(String orderId) {
        Order order = this.buy.remove(orderId);
        boolean result = false;
        if (null != order) {
            result = this.buyPrice.remove(order);
        } else {
            order = this.sell.remove(orderId);
            if (null != order) {
                result = this.sellPrice.remove(order);
            }
        }
        return result;
    }
    /**
     * Ищет совпадение.
     * @param treeSet список, в котором производится поиск.
     * @param order поступивший заказ.
     * @return заказ из очереди заказов.
     */
    public Order searchMatch(TreeSet<Order> treeSet, Order order) {
        Double price = order.getPrice();
        String op = order.getOperation();
        Order match = null;
        Iterator<Order> iter = treeSet.iterator();
        Order item = null;
        while (iter.hasNext()) {
            item = iter.next();
            if ("BUY".equals(op) && Double.compare(item.getPrice(), price) >= 0.0) {
                match = item;
                iter.remove();
                break;
            } else if ("SELL".equals(op) && Double.compare(item.getPrice(), price) <= 0.0) {
                match = item;
                iter.remove();
                break;
            }
        }
        return match;
    }
    /**
     * Возвращает суммарное количество заказов на покупку и продачу.
     * @return суммарное количество заказов на покупку и продачу.
     */
    public int size() {
        return this.buy.size() + this.sell.size();
    }
    /**
     * Сортирует списки заказов на покупку и продажу.
     *
    private void sort() {
        this.buyPrice.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o2.getPrice(), o1.getPrice());
            }
        });
        this.sellPrice.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
    }*/
}
