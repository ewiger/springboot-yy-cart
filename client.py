#!/usr/bin/env python
#
# Requires 2.7.11+
#
# pip install requests
#
import requests


URL_PRX = 'http://localhost:8080/cart'


def get_json(url):
    res = requests.get(url)
    if res.status_code != 200:
        print("HTTP request failed. Try `curl -vv %s`" % url)
        exit()
    return res.json()


def main():
    # Empty the basket first
    url = URL_PRX + '/empty'
    print('Requesting items from: %s' % url)
    res = requests.get(url)
    assert res.status_code == 200
    items_url = URL_PRX + '/items'
    print('Requesting items from: %s' % items_url)
    data = get_json(items_url)
    print('Received (%d) items' % len(data))
    assert len(data) == 0

    # Put products to the cart
    add_url = URL_PRX + '/add'
    print('Ok now put some products into the cart.: %s' % add_url)
    res = requests.post(add_url, data={'product': 'Apples', 'qty': 1})
    # we expect failure on non existing product
    assert res.status_code == 404
    assert 'No product found' in res.json()['message']
    # request with a proper payload
    res = requests.post(add_url, data={'product': 'Apple'})
    assert res.status_code == 200
    assert res.json()['quantity'] == 1
    # quantity must add up if the same product placed into cart
    res = requests.post(add_url, data={'product': 'Apple', 'qty': 2})
    assert res.status_code == 200
    assert res.json()['quantity'] == 3

    # Add more items to the cart
    res = requests.post(add_url, data={'product': 'Papaya'})
    assert res.status_code == 200
    assert res.json()['quantity'] == 1
    res = requests.post(add_url, data={'product': 'Orange'})
    assert res.status_code == 200
    assert res.json()['quantity'] == 1
    res = requests.post(add_url, data={'product': 'Garlic'})
    assert res.status_code == 200
    assert res.json()['quantity'] == 1
    # hmm, let's check the sum of the quote
    print('Requesting items from: %s' % items_url)
    data = get_json(items_url)
    assert len(data) == 4
    print(data)
    # It might be wrong but we apply discount for Papaya only if qty > 2
    cost_url = URL_PRX + '/cost'
    print('Requesting items from: %s' % cost_url)
    res = requests.get(cost_url)
    assert res.status_code == 200
    # print res.json()
    assert res.json() == 1.7

    # Finally, add more papaya and test the discount
    res = requests.post(add_url, data={'product': 'Papaya', 'qty': 4})
    assert res.status_code == 200
    assert res.json()['quantity'] == 5
    cost_url = URL_PRX + '/cost'
    print('Requesting items from: %s' % cost_url)
    res = requests.get(cost_url)
    assert res.status_code == 200
    # print res.json()
    # TODO: make a better test of the potential rounding errors
    assert res.json() == 2.7


if __name__ == '__main__':
    main()
