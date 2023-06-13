db = connect('mongodb://mongokeeper:mySecret!@localhost:27017/shop?authSource=admin');

db.users.insertMany([
    {
        _id: "user1",
        username: "John",
        password: "AmeduyhErROEsuzEC3UTgI3Y0Szh8Gd7S3hzEMfSCoc=",
        authorities: [
            "CUSTOMER"
        ]
    },
    {
        _id: "user2",
        username: "Laszlo",
        password: "ZvCF+EOLKQSyC3x94ZlC322ka2mTIqffRjHqB0e8fOk=",
        authorities: [
            "CUSTOMER"
        ]
    },
    {
        _id: "user3",
        username: "Christina",
        password: "41EPTN/rdDbDcSPc2vnsVpXkvWRwO1gFGGD2EWzEm5Q=",
        authorities: [
            "ADMIN"
        ]
    }
])

db.products.insertMany([
    {
        _id: "pball1",
        name: 'Ball',
        description: 'Brand new soccer ball.',
        price: 35.99
    },
    {
        _id: "pbicycle1",
        name: 'Bicycle',
        description: 'Red bicycle with big basket on the front.',
        price: 220.00
    },
    {
        _id: "prope1",
        name: 'Jumping rope',
        description: 'Super fast jumping rope.',
        price: 8.99
    }
]);

db.orders.insertMany([
    {
        _id: ObjectId("648217d3a061235b73beafb4"),
        customerId: "user1",
        orderItems: [
            {
                productId: "pball1",
                quantity: 3
            },
            {
                productId: "pbicycle1",
                quantity: 1
            }
        ],
        _class: "com.mieker.sportshop.domain.model.Order"
    },
    {
        _id: ObjectId("648217d3a061235b73beafb5"),
        customerId: "user2",
        orderItems: [
            {
                productId: "prope1",
                quantity: 1
            }
        ],
        _class: "com.mieker.sportshop.domain.model.Order"
    }
]);