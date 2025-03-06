const express = require('express');
const router = express.Router();

// Lay models

const User = require('../models/users');
const Fruits = require('../models/fruits');
const FruitsID = require('../models/fruitshaveid');

// Phan POST: day len 

router.post('/add-user', async (req,res) =>{
    try {
        const data = req.body;
        const newUser = new User({
            userName: data.userName,
            passWord: data.passWord,
            nameUser: data.nameUser,
            avatar: data.avatar,
            role: data.role
        })
        const result = await newUser.save();
        if (result) {
            res.json({
                status: 200,
                message: "Them thanh cong",
                data: result
            })
        } else {
            res.json({
                status: 400,
                message: 'Them that bai',
                data: []
            })
        }
    } catch (error) {
        console.log(error);
    }
})

router.post('/add-fruits', async(req,res) => {
    try {
        const data = req.body;
        const newFruits = new Fruits({
            nameFruit: data.nameFruit,
            quantityFruits: data.quantityFruits,
            priceFruits: data.priceFruits,
            statusFruits: data.statusFruits,
            imageFruits: data.imageFruits,
            descriptionFruits: data.descriptionFruits
        })
        const result = await newFruits.save();
        if (result) {
            res.json({
                status: 200,
                message: 'Them thanh cong',
                data: result
            })
        } else {
            res.json({
                status: 400,
                message: 'Them that bai',
                data: []
            })
        }
    } catch (error) {
        console.log(error);
    }
}) 

router.post('/add-fruits-have-id', async(req,res) =>{
    try {
        const data = req.body
        const newFruitsHaveID = new FruitsID({
            nameFruitsUserHave: data.nameFruitsUserHave,
            quantityFruitsUserHave: data.quantityFruitsUserHave,
            priceFruitsUserHave: data.priceFruitsUserHave,
            imageFruitsuserHave: data.imageFruitsuserHave,
            id_user: data.id_user
        })
        const result = await newFruitsHaveID.save();
        if (result) {
            res.json({
                status: 200,
                message: 'Them thanh cong',
                data: result
            })
        } else {
            res.json({
                status: 400,
                message: 'Them that bai',
                data: []
            })
        }
    } catch (error) {
        console.log(error);
    }
})
// Phan GET: lay du lieu
router.get('/getUser', async(req,res) =>{
    try {
        const data = await User.find();
        res.json({
            status: 200,
            message: 'Find',
            data: data
        })
    } catch (error) {
        res.json({
            status: 400,
            message: 'Not found',
            data: []
        })
    }
})

router.get('/get-user-by-id', async(req,res) =>{
    try {
        const id = req.query._id;
        if (!id) {
            return res.json({
                status: 400,
                message: 'Not Found',
                data: []
            })
        }
        const data = await User.find({_id:id}).populate('_id');
        if(data){
            res.json({
                status: 200,
                message: 'Found',
                data: data
            })
        }
    } catch (error) {
        console.log(error);
        res.json({
            status: 400,
            message: 'Not Found',
            data: []
        })
    }
})

router.get('/get-fruits', async (req,res) =>{
    try {
        const data = await Fruits.find();
        res.json({
            status: 200,
            message: 'Found',
            data: data
        })
    } catch (error) {
        res.json({
            status: 400,
            message: 'Not Found',
            data: []
        })
    }
})

router.get('/get-fruits-have-id/:id', async(req,res)=>{
    try {
        const data = await FruitsID.find().populate('id_user');
        res.json({
            status: 200,
            message: 'Found',
            data: data
        })
    } catch (error) {
        res.json({
            status: 400,
            message: 'Not Found',
            data: []
        })
    }
})

router.get('/get-fruit-have-id', async(req,res) =>{
    try {
        const userID = req.query.id_user;
        if (!userID) {
            return res.json({
                status: 400,
                message: 'Not Found ID',
                data: []
            })
        }
        const data = await FruitsID.find({id_user: userID}).populate('id_user');
        if (data.length === 0) {
            res.json({
                status: 400,
                message: 'No data found for this user',
                data: []
            })
        }
        return res.json({
            status: 200,
            message: 'Found',
            data: data
        })
    } catch (error) {
        console.log(error);
        return res.json({
            status: 400,
            message: 'Error Server',
            data: []
        })
    }
})

router.get('/get-fruits-by-id-fruits', async(req,res) =>{
    try {
        const id_fruits = req.query._id
        if (!id_fruits) {
            return res.json({
                status: 400,
                message: 'Not Found',
                data: []
            })
        }
        const result = await Fruits.find({_id: id_fruits}).populate('_id');
        if (result.length === 0) {
            return res.json({
                status: 400,
                message: 'Not Found',
                data: []
            })
        }
        return res.json({
            status: 200,
            message: 'Found',
            data: result
        })
    } catch (error) {
        console.log(error);
        return res.json({
            status: 400,
            message: 'Not Found',
            data: []
        })
    }
})
// Phan update: cap nhat san pham ==> Danh cho admin

router.put('/update-user-by-id/:id', async(req,res) => {
    try {
        const id = req.params.id.replace(/[^a-fA-F0-9]/g,'');
        const data = req.body;
        const updateUser = await User.findById(id);
        let result = null;
        if (updateUser) {
            updateUser.userName = data.userName ?? updateUser.nameUser
            updateUser.passWord = data.passWord ?? updateUser.passWord
            updateUser.nameUser = data.nameUser ?? updateUser.nameUser
            updateUser.avatar = data.avatar ?? updateUser.avatar
            updateUser.role = data.role ?? updateUser.role
            result = await updateUser.save();
        }
        if (result) {
            res.json({
                status: 200,
                message: 'Cap nhat thanh cong',
                data: result
            })
        }else{
            res.json({
                status: 400,
                message: 'Cap nhat that bai',
                data: []
            })
        }
    } catch (error) {
        console.log(error);
    }
})

router.put('/update-fruits-by-id/:id', async(req,res) =>{
    try {
        const id = req.params.id.replace(/[^a-fA-F0-9]/g,'');
        const data = req.body
        const update = await Fruits.findById(id)
        let result = null;
        if (update) {
            update.nameFruit = data.nameFruit ?? update.nameFruit
            update.quantityFruits = data.quantityFruits ?? update.quantityFruits
            update.priceFruits = data.priceFruits ?? update.priceFruits
            update.statusFruits = data.statusFruits ?? update.statusFruits
            update.imageFruits = data.imageFruits ?? update.imageFruits
            update.descriptionFruits = data.descriptionFruits ?? update.descriptionFruits
            result = await update.save();
        }
        if (result) {
            res.json({
                status: 200,
                message: 'Cap nhat thanh cong',
                data: result
            })
        }
    } catch (error) {
        console.log(error);
        res.json({
            status: 400,
            message: 'Cap nhat TB',
            data: []
        })
    }
})

// Phan delete: xoa 

router.delete('/deletefruitsByID/:id', async (req,res) =>{
    try {
        const id = req.params.id.replace(/[^a-fA-F0-9]/g,'');
        const result = await Fruits.findByIdAndDelete(id)
        if(result){
            res.json({
                status: 200,
                message: 'Xoa thanh cong',
                data: result
            })
        }
    } catch (error) {
        console.log(error);
        res.json({
            status: 400,
            message: 'Loi',
            data: []
        })
    }
})
// Phan export
module.exports = router;