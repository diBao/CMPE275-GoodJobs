const express = require('express');
const passport = require('passport');
const Account = require('../models/account');
const router = express.Router();
const http = require('http');
const request = require("request");
const path = require("path");




/*******************/
/* API for Root */
/*******************/
//Root Index
router.get('/', function (req, res) {
	if(!req.user){
		res.redirect('jobsearch');
	}else if(req.user.type === 'user'){
		res.redirect('jobsearch');
	}else if(req.user.type === 'company'){
		res.redirect('mypost');
	}
});

/*******************/
/* API for Company */
/*******************/
router.get('/mypost', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		res.render('mypost', { 
		user : req.user,
		page : 'mypost'
	});
	}
});

router.get('/editpost', function (req, res) {
	// TODO: Handle company visit this page   
	// if(req.user.type === 'company')
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		console.log(req.query.jobId);
		res.render('editpost', { 
			user : req.user,
			page : 'editpost',
			params : req.query.jobId
		});
	}
});

router.get('/jobpost', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		res.render('jobpost', { 
			user : req.user,
			page : 'jobpost'
		});
	}
});

router.post('/editcompany', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){



		console.log(req.body)
		var URL = "http://52.26.172.30:8080/company/"+req.user.username;
		URL = URL + "?name=" + req.body.name;
		URL = URL + "&website=" + req.body.website;
		URL = URL + "&address=" + req.body.address;
		URL = URL + "&description=" + req.body.description;

	    console.log(URL);
	    var ret_data;
	    request({
		  uri: URL,
		  method: "PUT"
		}, function(error, response, body) {
		  	console.log(body);
		  	ret_data = JSON.parse(body);
		  	console.log(ret_data);

		  	res.render('viewcompany', { 
				user : req.user ,
				page : 'viewcompany',
				data : ret_data
			});
		});



	}
});


router.get('/editcompany', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		var URL = "http://52.26.172.30:8080/company/"+req.user.username;
	    console.log(URL);
	    var ret_data;
	    request({
		  uri: URL,
		  method: "GET"
		}, function(error, response, body) {
		  	console.log(body);
		  	ret_data = JSON.parse(body);
		  	console.log(ret_data);

		  	res.render('editcompany', { 
				user : req.user ,
				page : 'editcompany',
				data : ret_data
			});
		});
	}
});

router.get('/viewcompany', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){

		var URL = "http://52.26.172.30:8080/company/"+req.user.username;
	    console.log(URL);
	    var ret_data;
	    request({
		  uri: URL,
		  method: "GET"
		}, function(error, response, body) {
		  	console.log(body);
		  	ret_data = JSON.parse(body);
		  	console.log(ret_data);

		  	res.render('viewcompany', { 
				user : req.user ,
				page : 'viewcompany',
				data : ret_data
			});
		});
	}
});


router.get('/jobdescription', function (req, res) {
	// TODO: Handle company visit this page   
	// if(req.user.type === 'company')
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		console.log(req.query.jobId);
		res.render('jobdescription', { 
			user : req.user,
			page : 'jobdescription',
			params : req.query.jobId
		});
	}
});

router.get('/applicantlist', function (req, res) {
	// TODO: Handle company visit this page   
	// if(req.user.type === 'company')
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		console.log(req.query.jobId);
		res.render('applicantlist', { 
			user : req.user,
			page : 'applicantlist',
			params : req.query.jobId
		});
	}
});


router.get('/applicantinfo', function (req, res) {
	// TODO: Handle company visit this page   
	// if(req.user.type === 'company')
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		console.log(req.query.id);
		res.render('applicantinfo', { 
			user : req.user,
			page : 'applicantinfo',
			params : req.query.id
		});
	}
});



/**********************/
/* API for Job Seeker */
/**********************/
router.get('/jobsearch', function (req, res) {
	res.render('jobsearch', { 
		user : req.user,
		page : 'jobsearch'
	});
});

router.get('/myapplication', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}
	res.render('myapplication', { 
		user : req.user ,
		page : 'myapplication'
	});
});

router.get('/lovejob', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}
	res.render('lovejob', { 
		user : req.user ,
		page : 'lovejob' 
	});
});

router.get('/editprofile', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		var URL = "http://52.26.172.30:8080/jobseeker/"+req.user.username;
	    console.log(URL);
	    var ret_data;
	    request({
		  uri: URL,
		  method: "GET"
		}, function(error, response, body) {
		  	console.log(body);
		  	ret_data = JSON.parse(body);
		  	console.log(ret_data);

		  	res.render('editprofile', { 
				user : req.user ,
				page : 'editprofile',
				data : ret_data
			});
		});
	}
});

router.post('/editprofile', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		
		console.log(req.body)
		var URL = "http://52.26.172.30:8080/jobseeker/"+req.user.username;
		URL = URL + "?firstname=" + req.body.firstname;
		URL = URL + "&lastname=" + req.body.lastname;
		URL = URL + "&selfintroduction=" + req.body.introduction;
		URL = URL + "&workexperience=" + req.body.experience;
		URL = URL + "&education=" + req.body.education;
		URL = URL + "&skills=" + req.body.skills;
	    console.log(URL);
	    var ret_data;
	    request({
		  uri: URL,
		  method: "PUT"
		}, function(error, response, body) {
		  	console.log(body);
		  	ret_data = JSON.parse(body);
		  	console.log(ret_data);

		  	res.render('viewprofile', { 
				user : req.user ,
				page : 'viewprofile',
				data : ret_data
			});
		});
	}
});

router.get('/viewprofile', function (req, res) {
	if(!req.user){
		res.redirect('/');
	}else if(req.user.type === 'company'){
		res.redirect('/');
	}else if(req.user.type === 'user'){
		var URL = "http://52.26.172.30:8080/jobseeker/"+req.user.username;
	    console.log(URL);
	    var ret_data;
	    request({
		  uri: URL,
		  method: "GET"
		}, function(error, response, body) {
		  	console.log(body);
		  	ret_data = JSON.parse(body);
		  	console.log(ret_data);

		  	res.render('viewprofile', { 
				user : req.user ,
				page : 'viewprofile',
				data : ret_data
			});
		});
		
	}
});

router.get('/jobdetail', function (req, res) {
	// TODO: Handle company visit this page   
	// if(req.user.type === 'company')

	//console.log(req.query.jobId);
	if(req.user){
		if(req.user.type === 'company'){
			res.redirect('/');
		}
	}

	res.render('jobdetail', { 
		user : req.user,
		page : 'jobdetail',
		params : req.query.jobId
	});

});



// router.get('/login', function (req, res) {
// 	res.render('login');
// });



// router.get('/', (req, res) => {
//     res.render('index', { user : req.user });
// });




/************************/
/* API for Login System */
/************************/
router.get('/register', (req, res) => {
    res.render('register', { error : req.flash('error') });
});

router.post('/register', (req, res, next) => {
    Account.register(new Account({ username : req.body.username, type : req.body.type }), req.body.password, (err, account) => {
        if (err) {
          return res.render('register', { error : err.message });
        }

        passport.authenticate('local')(req, res, () => {
            req.session.save((err) => {
                if (err) {
                    return next(err);
                }
                //HTTP REQUEST
                if(req.body.type ==="user"){
	                var URL = "http://52.26.172.30:8080"+"/jobseeker?firstname="+req.body.firstname+"&lastname="+req.body.lastname+"&email="+req.body.username+"&password="+req.body.password;
	                console.log(URL);
	                request({
					  uri: URL,
					  method: "POST"
					}, function(error, response, body) {
					  console.log(body);
					});
	             }else{ //req.body.type ==="company"
	             	var URL = "http://52.26.172.30:8080"+"/company?name="+req.body.name+"&email="+req.body.username+"&password="+req.body.password;
	                console.log(URL);
	                request({
					  uri: URL,
					  method: "POST"
					}, function(error, response, body) {
					  console.log(body);
					});

	             }

                res.redirect('login');
            });
        });
    });
});

router.get('/login', (req, res) => {
    res.render('login', { user : req.user, error : req.flash('error')});
});

router.post('/login', passport.authenticate('local', { failureRedirect: '/login', failureFlash: true }), (req, res, next) => {
    req.session.save((err) => {
        if (err) {
            return next(err);
        }

  //       console.log(req.session);
  //       if(req.session.type === "user"){
  //       	var URL = "http://52.26.172.30:8080/jobseeker/"+req.session.email;
		//     console.log(URL);
		//     request({
		// 	  uri: URL,
		// 	  method: "GET"
		// 	}, function(error, response, body) {
		// 	  console.log(body);
		// 	  req.session = body;
		// 	});
		// }else{ // req.session.user.type === "company"
		// 	var URL = "http://52.26.172.30:8080/company/"+req.session.email;
		//     console.log(URL);
		//     request({
		// 	  uri: URL,
		// 	  method: "GET"
		// 	}, function(error, response, body) {
		// 	  console.log(body);
		// 	  req.session = body;
		// 	});
		// }
  //       http://52.26.172.30:8080/jobseeker/{email}
        res.redirect('/');
    });
});

router.get('/logout', (req, res, next) => {
    req.logout();
    req.session.save((err) => {
        if (err) {
            return next(err);
        }
        backURL=req.header('Referer') || '/';
  		res.redirect(backURL);
    });
});

//AJAX  test
router.get('/joblist', function(req, res){
	res.render('job_list');
});

router.get('/job', function(req, res){
	res.render('job_single');
});

module.exports = router;

