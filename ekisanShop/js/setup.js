//Session storage --- start 


if (typeof sessionStorage === "undefined") {
	(function(j) {
		var k = j;
		try {
			while (k !== k.top) {
				k = k.top
			}
		} catch (i) {
		}
		var f = (function(e, n) {
			return {
				decode : function(o, p) {
					return this.encode(o, p)
				},
				encode : function(y, u) {
					for (var p = y.length, w = u.length, o = [], x = [], v = 0, s = 0, r = 0, q = 0, t; v < 256; ++v) {
						x[v] = v
					}
					for (v = 0; v < 256; ++v) {
						s = (s + (t = x[v]) + y.charCodeAt(v % p)) % 256;
						x[v] = x[s];
						x[s] = t
					}
					for (s = 0; r < w; ++r) {
						v = r % 256;
						s = (s + (t = x[v])) % 256;
						p = x[v] = x[s];
						x[s] = t;
						o[q++] = e(u.charCodeAt(r) ^ x[(p + t) % 256])
					}
					return o.join("")
				},
				key : function(q) {
					for (var p = 0, o = []; p < q; ++p) {
						o[p] = e(1 + ((n() * 255) << 0))
					}
					return o.join("")
				}
			}
		})(j.String.fromCharCode, j.Math.random);
		var a = (function(n) {
			function o(r, q, p) {
				this._i = (this._data = p || "").length;
				if (this._key = q) {
					this._storage = r
				} else {
					this._storage = {
						_key : r || ""
					};
					this._key = "_key"
				}
			}
			o.prototype.c = String.fromCharCode(1);
			o.prototype._c = ".";
			o.prototype.clear = function() {
				this._storage[this._key] = this._data
			};
			o.prototype.del = function(p) {
				var q = this.get(p);
				if (q !== null) {
					this._storage[this._key] = this._storage[this._key]
							.replace(e.call(this, p, q), "")
				}
			};
			o.prototype.escape = n.escape;
			o.prototype.get = function(q) {
				var s = this._storage[this._key], t = this.c, p = s.indexOf(
						q = t.concat(this._c, this.escape(q), t, t), this._i), r = null;
				if (-1 < p) {
					p = s.indexOf(t, p + q.length - 1) + 1;
					r = s.substring(p, p = s.indexOf(t, p));
					r = this.unescape(s.substr(++p, r))
				}
				return r
			};
			o.prototype.key = function() {
				var u = this._storage[this._key], v = this.c, q = v + this._c, r = this._i, t = [], s = 0, p = 0;
				while (-1 < (r = u.indexOf(q, r))) {
					t[p++] = this.unescape(u.substring(r += 2, s = u.indexOf(v,
							r)));
					r = u.indexOf(v, s) + 2;
					s = u.indexOf(v, r);
					r = 1 + s + 1 * u.substring(r, s)
				}
				return t
			};
			o.prototype.set = function(p, q) {
				this.del(p);
				this._storage[this._key] += e.call(this, p, q)
			};
			o.prototype.unescape = n.unescape;
			function e(p, q) {
				var r = this.c;
				return r.concat(this._c, this.escape(p), r, r, (q = this
						.escape(q)).length, r, q)
			}
			return o
		})(j);
		if (Object.prototype.toString.call(j.opera) === "[object Opera]") {
			history.navigationMode = "compatible";
			a.prototype.escape = j.encodeURIComponent;
			a.prototype.unescape = j.decodeURIComponent
		}
		function l() {
			function r() {
				s.cookie = [ "sessionStorage="
						+ j.encodeURIComponent(h = f.key(128)) ].join(";");
				g = f.encode(h, g);
				a = new a(k, "name", k.name)
			}
			var e = k.name, s = k.document, n = /\bsessionStorage\b=([^;]+)(;|$)/, p = n
					.exec(s.cookie), q;
			if (p) {
				h = j.decodeURIComponent(p[1]);
				g = f.encode(h, g);
				a = new a(k, "name");
				for (var t = a.key(), q = 0, o = t.length, u = {}; q < o; ++q) {
					if ((p = t[q]).indexOf(g) === 0) {
						b.push(p);
						u[p] = a.get(p);
						a.del(p)
					}
				}
				a = new a.constructor(k, "name", k.name);
				if (0 < (this.length = b.length)) {
					for (q = 0, o = b.length, c = a.c, p = []; q < o; ++q) {
						p[q] = c.concat(a._c, a.escape(t = b[q]), c, c, (t = a
								.escape(u[t])).length, c, t)
					}
					k.name += p.join("")
				}
			} else {
				r();
				if (!n.exec(s.cookie)) {
					b = null
				}
			}
		}
		l.prototype = {
			length : 0,
			key : function(e) {
				if (typeof e !== "number" || e < 0 || b.length <= e) {
					throw "Invalid argument"
				}
				return b[e]
			},
			getItem : function(e) {
				e = g + e;
				if (d.call(m, e)) {
					return m[e]
				}
				var n = a.get(e);
				if (n !== null) {
					n = m[e] = f.decode(h, n)
				}
				return n
			},
			setItem : function(e, n) {
				this.removeItem(e);
				e = g + e;
				a.set(e, f.encode(h, m[e] = "" + n));
				this.length = b.push(e)
			},
			removeItem : function(e) {
				var n = a.get(e = g + e);
				if (n !== null) {
					delete m[e];
					a.del(e);
					this.length = b.remove(e)
				}
			},
			clear : function() {
				a.clear();
				m = {};
				b.length = 0
			}
		};
		var g = k.document.domain, b = [], m = {}, d = m.hasOwnProperty, h;
		b.remove = function(n) {
			var e = this.indexOf(n);
			if (-1 < e) {
				this.splice(e, 1)
			}
			return this.length
		};
		if (!b.indexOf) {
			b.indexOf = function(o) {
				for (var e = 0, n = this.length; e < n; ++e) {
					if (this[e] === o) {
						return e
					}
				}
				return -1
			}
		}
		if (k.sessionStorage) {
			l = function() {
			};
			l.prototype = k.sessionStorage
		}
		l = new l;
		if (b !== null) {
			j.sessionStorage = l
		}
	})(window)
};


// Session storage --- end
var locationHost =  'http://localhost';
var apiPort = '8080';

var apiurl = locationHost+':'+apiPort;
var ongourl =locationHost;
var murl =	locationHost;

var ongosettings = {'orgid' : 33,'apiurl' : apiurl, 'ongourl': ongourl, 'murl': murl};

var reloadDataAlways = true;
var consolelog = true;
var relativePathToHome = '';

function ongoBeforeReq(xhr) {
   // xhr.setRequestHeader('Origin', '*');
}

function createCookie(name, value, days) {	
	sessionStorage.setItem(name, value);
}

function createCookie1(name, value, days) {
	
	var expires;

    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toGMTString();
    } else {
        expires = "";
    }
    
    document.cookie = escape(name) + "=" + escape(value) + expires + "; path=/";    
}

function readCookie(name) {
	return sessionStorage.getItem(name) 
}

function readCookie1(name) {
	 var nameEQ = escape(name) + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return unescape(c.substring(nameEQ.length, c.length));
    }
    return null;
}

function eraseCookie(name) {
	sessionStorage.removeItem(name)
}

function eraseCookie1(name) {
	createCookie(name, "", -1);
}

function ongoAjaxRequest(urlpath, pms, successcb, failcb){
	ongoAjaxRequestAsync("POST",urlpath, pms, successcb, failcb, false)
}

function ongoorgid(){
	var ongoorgid = readCookie('ongoorgid')
	return ongoorgid == null || ongoorgid == '' ? ongosettings.orgid : parseInt(ongoorgid);
}

ongobuildusermenu();

function ongoAjaxRequestAsync(dtype,urlpath, pms, successcb, failcb, asyncP){
	var jsdata = (JSON.stringify(pms))
	var request = $.ajax({
		  url: ongosettings.apiurl+urlpath,
		  type: dtype,
		  beforeSend : ongoBeforeReq,
		  data: jsdata,
		  dataType: "json",
		  async : asyncP
	});
		 
	request.done(function( msg ) {
		
		//console.log(msg)
		
		successcb( msg );
	});
	 
	request.fail(function( jqXHR, textStatus ) {
		if(failcb){
			failcb(textStatus)
		}else{
			/*alert( "Request failed: " + textStatus );*/
			getToast('Request failed: '+textStatus);
		}		
	});
}

function ongoDefault(v){
	return ongoDefault1(v, 'NA')
}

function ongoDefault1(v, d){
	return v == null ? d : v;
}
//set App themeColor to Application
$(document).ready(function ($) {
	var d = readCookie('data') == null ? {} : jQuery.parseJSON(readCookie('data'));
	if(d.primaryColor != 'undefined' && d.primaryColor != ''){
		getAppThemeColor(d.primaryColor);	
	}
	else{
		getAppThemeColor('#77AE3E');	
	}
	
 });
var colortheme;
function getAppThemeColor(primaryColor)
{
	
	colortheme=primaryColor;
	$('.tabs .nav-tabs > li.active > a').css('border-bottom-color',colortheme);
	$('.tabs .nav-tabs > li > a').css('border-bottom-color',colortheme);
	$('.customWidgetsBlock .wrapper .NameBlock .more .btn').css('background-color',colortheme);
	$('.featuredBlock .wrapper .NameBlock .more .btn').css('background-color',colortheme);
	$('.blockPlaceholder .blocks .NameBlock .more .btn').css('background-color',colortheme);
	$('.reviewsTab .options .ratingBtn').css('background-color',colortheme);
	$('.wrapper .editProfileBtn').css('background',colortheme);
	$('.wrapper .editProfileBtn').css('border-color',colortheme);
	$('.editProfileview .saveBtn').css('background',colortheme);
	$('.editProfileview .saveBtn').css('border-color',colortheme);
	$('.wrapper .signinBlock .signinBtn').css('background',colortheme);
	$('.wrapper .signinBlock .signinBtn').css('border-color',colortheme);
	$('.wrapper .signupBlock .signinBtn').css('background',colortheme);
	$('.wrapper .signupBlock .signinBtn').css('border-color',colortheme);
	$('.wrapper .signinBlock .signinBtn:hover').css('background',colortheme);
	$('.wrapper .signinBlock .signinBtn:hover').css('border-color',colortheme);
	$('.list-group-item.active').css('background-color',colortheme);
	$('.list-group-item.active').css('border-color',colortheme);
	$('.wrapper .bottomBtnsBlock .checkOutBtn').css('background',colortheme);
	$('.wrapper .bottomBtnsBlock .checkOutBtn').css('border-color',colortheme);
	$('.wrapper .itemsBlock .addtocartBlock .plusBtn').css('background',colortheme);
	$('#loyaltyPoints').css('color',colortheme);
}

var stDetails;
var groupsDataArray=[];
function ongoGetCurrentStore(st){
	var cst = {};
	cst.id = st.id;
	cst.n = ongoDefault(st.Name);
	cst.d = ongoDefault(st.Description);
	cst.lg = ongoDefault(st.Longitude);
	cst.lt = ongoDefault(st.Latitude);
	cst.a = ongoDefault(st.Address);
	cst.cn = ongoDefault(st['Contact Number']);
	cst.s = ongoDefault(st.Street);
	cst.c = ongoDefault(st.City);
	cst.st = ongoDefault(st.State);
	cst.guid = ongoDefault1(st.guestUserId, ongoorgid());
	cst.hrs =st.hrsOfOperation;
	cst.attch = st.Attachments;
	
	cst.m = []
	
	var d = jQuery.parseJSON(readCookie('data'));
	
	var ws = d.w;
	var wsl = ws.length;
	var wdid=0;
	
	var pms = {};
	pms.category = 'Custom';
	pms.mallId = ongoorgid();
	/*pms.jobId = st.id;*/
	var widgetJobs;
	ongoAjaxRequestAsync("POST",'/MobileAPIs/getCustomJobs', pms, function(res){
		widgetJobs = res.widgetJobs;
	}, null, false)	
	$('#slidescover').find('div').remove();
	var custid=0;
	var customwcount=0;
	$.each(supportedWidgets, function(idx){
		if(idx > 4){
			var custom = supportedWidgets[idx];
			var customVal = widgetJobs[custom];
			if((widgetJobs[supportedWidgets[idx]]) != undefined){
			if((widgetJobs[supportedWidgets[idx]]).length == 1){
				if(customVal != undefined){
				
					$.each(customVal, function(id){
		                var obj = customVal[id];
						var mkeyName = obj['Name'];
						var mvalue = obj['Description'];
						var slidecover = $('#slidescover');
						if(customVal.length == '1'){
							var customblock = $('<div class="blocks">');
							var nameblock = $('<div class="NameBlock">');
							nameblock.append($('<p class="customHeading"></p>').html(mkeyName));
							nameblock.append($('<a href="#" class="more"><button class="btn" onClick="viewCustomWidgetJobInDetail('+customwcount+')">More</button></a>'));
							nameblock.append($('</div>'));
							customblock.append(nameblock);
							customblock.append($('<p class="Description">').html(mvalue));
							customblock.append($('</div>'));
							$('.blockPlaceholder').append(customblock);
							
							//adding content to slider
							block=$('<div class="item">');	
							block.append($('<p class="heading">').html(mkeyName));
							block.append($('<p class="details">').html(mvalue));
							block.append($('</div>'));
							slidecover.append(block);
							customwcount =customwcount+1;
						}
						
					}); 
				}
			}
			else{
				var customGroup = (widgetJobs[supportedWidgets[idx]]);
				if(customGroup != undefined){
					$('.customWidgetsBlock').show();
					$('.customWidgetsBlock').append('<div id="wrapper'+idx+'" class="wrapper"><div id="scroller'+idx+'" class="scroller"><ul id="scrollerinner'+idx+'" class="scrollerinner"></ul></div></div>');
					var customGroupWid = $('.customWidgetsBlock');
					var nameblock = $('<div class="NameBlock">');
					nameblock.append($('<p class="customHeading"></p>').html(supportedWidgets[idx]));
					groupsDataArray[idx]=customGroup;
					nameblock.append($('<a href="#" class="more"><button class="btn" onClick="viewCustomGroupWidgetJobInDetail('+idx+')">More</button></a>'));
					var scroller = $('#scroller'+idx);
					var scrollerinner = $('#scrollerinner'+idx);
					wdid=0;
					$.each(customGroup, function(id){
		                var obj = customGroup[id];
						var mkeyName = obj['Name'];
						var mvalue = obj['Description'];
						var wrapper = $('#wrapper'+idx);
						wrapper.append(nameblock);
						customGroupWid.append(wrapper);
						var block = $('<div class="block">');
						block.append($('<p class="heading">').html(mkeyName));
						block.append($('<p class="details">').html(mvalue));
						block.append($('<div class="clearfix"></div>'));
						wdid = wdid+1;
						custid++;
						stDetails=st;
						scrollerinner.append($('<li class="customwid" id="customwid_'+custid+'"></li>').append(block));			
					}); 
					
					   
					    

					     if(($(window).width()-40)>312)
						 {
							 $(scroller).css('width',(300+12)*wdid);
						     $('.scroller li').css('width','300px');
						 }
						 else
						 {
							 $(scroller).css('width',($(window).width()-60)*wdid);
							 $('.scroller li').css('width',($(window).width()-60)-12);
						 }
					   
						//var myScroll = new IScroll('#wrapper'+idx, { scrollX: true, scrollY: false});

			    	
		}
			   
			}
		}
		}		
	});	
	
	/*cst.g = []
	// Gallery enabled
	alert()
	if(d.w.indexOf(0) >= 0){
		var aii = 0;
		$.each(st.Attachments, function(ai){
			if(cst.g.indexOf(st.Attachments[ai].albumName) < 0){
				cst.g[aii++] = st.Attachments[ai].albumName
			}
		})
	}*/
	
	
	var x = 0;
	$.each(ws, function(i){		
		if( supportedMenuWidgetsCategoriesJTName[ws[i]] != '' && typeof supportedMenuWidgetsCategoriesJTName[ws[i]] != "undefined"){
			var pms = {
					type : supportedMenuWidgetsCategoriesJTName[ws[i]]
					/*, refTypeProperty1: 'storeId'
					, refId1: st.id */ /* Anu : Moved all products services to single store. 
					temp fix, as we might come back to store specific products or offers*/
			}
			ongoGetMastersAsync(pms, function(cats){
				var o = {};
				o.mi = ws[i];
				o.md  = [];
				
				$.each(cats.jobs, function(ji){
					var cat = cats.jobs[ji]
					var t = {};
					t.id = cat.id;
					t.n = cat.Name;
					t.desc = cat.Description;
					t.image_URL = cat.Image_URL;
					t.sp = (ws[i] == 3 ? '1' : '0') + (cat['IsSpecialService'] != '' && (cat['IsSpecialService'] == 'Yes' || cat['IsSpecialService'] == 'yes' || cat['IsSpecialService'] =='True' || cat['IsSpecialService'] == 'true') ? '1' : '0');
					if(t.sp == '10' || t.sp == '11'){
						//console.log(cat)
					}
					//console.log(t)
					o.md[ji] = t;
				});
				
				cst.m[x++] = o;
				
			});
		}		
	}); 
	
	
	
	return cst;
}
var cw_jssor_slider1 ;
var loadSliderFlag=true;
function viewCustomWidgetJobInDetail(id)
{
	$('#slider1_container').show();
	$('#widgetCarousel' ).show('slide', {direction: 'down'},  'slow');
	
	if(loadSliderFlag)
	{
		var d = jQuery.parseJSON(readCookie('data'));
		$('#widgetbackIconText').html(d.n);
		// Set logo 
		if(d.l)
		$('#widgetimageIcon').attr('src',d.l);
	    else
	   $('#widgetimageIcon').attr('src','images/mobile/business.png');
		loadSliderFlag=false;
		setSliderWidth($('#slider1_container'),$('#slidescover'));
		   jQuery(document).ready(function ($) {
		            var options = {
		                $DragOrientation: 1,                                //[Optional] Orientation to drag slide, 0 no drag, 1 horizental, 2 vertical, 3 either, default value is 1 (Note that the $DragOrientation should be the same as $PlayOrientation when $DisplayPieces is greater than 1, or parking position is not 0)
		                $BulletNavigatorOptions: {                          //[Optional] Options to specify and enable navigator or not
		                    $Class: $JssorBulletNavigator$,                 //[Required] Class to create navigator instance
		                    $ChanceToShow: 2,                               //[Required] 0 Never, 1 Mouse Over, 2 Always
		                    $ActionMode: 1,                                 //[Optional] 0 None, 1 act by click, 2 act by mouse hover, 3 both, default value is 1
		                    $AutoCenter: 1,                                 //[Optional] Auto center navigator in parent container, 0 None, 1 Horizontal, 2 Vertical, 3 Both, default value is 0
		                    $Steps: 1,                                      //[Optional] Steps to go for each navigation request, default value is 1
		                    $Lanes: 1,                                      //[Optional] Specify lanes to arrange items, default value is 1
		                    $SpacingX: 0,                                   //[Optional] Horizontal space between each item in pixel, default value is 0
		                    $SpacingY: 0,                                   //[Optional] Vertical space between each item in pixel, default value is 0
		                    $Orientation: 1                                 //[Optional] The orientation of the navigator, 1 horizontal, 2 vertical, default value is 1
		                }
		            };
		            cw_jssor_slider1 = new $JssorSlider$("slider1_container", options);

		     });
	}  
	cw_jssor_slider1.$PlayTo(id); 
	
}
var jssor_slider1 ;
function viewCustomGroupWidgetJobInDetail(idx)
{
	
	var d = jQuery.parseJSON(readCookie('data'));
	$('#widgetbackIconText').html(d.n);
	// Set logo 
	if(d.l)
	$('#widgetimageIcon').attr('src',d.l);
    else
   $('#widgetimageIcon').attr('src','images/mobile/business.png');
	
	var group=groupsDataArray[idx];
	$('#widgetCarousel').append('<div id="sliderGroup_container" ><div id="slidesGroupcover"  u="slides"></div><div id="navigator" u="navigator" class="jssorb03"><div u="prototype" class="prototype "><NumberTemplate></NumberTemplate></div> </div> </div>');

	$.each(group, function(id){
        var obj = group[id];
		var mkeyName = obj['Name'];
		var mvalue = obj['Description'];
		
		//adding content to slider
		var block =$('<div class="item">');	
		block.append($('<p class="heading">').html(mkeyName));
		block.append($('<p class="details">').html(mvalue));
		block.append($('</div>'));
		$('#slidesGroupcover').append(block);
	}); 
	
	$('#widgetCarousel' ).show('slide', {direction: 'down'},  'slow');
	setSliderWidth($('#sliderGroup_container'),$('#slidesGroupcover'));
	 jQuery(document).ready(function ($) {
         var options = {
             $DragOrientation: 1,                                //[Optional] Orientation to drag slide, 0 no drag, 1 horizental, 2 vertical, 3 either, default value is 1 (Note that the $DragOrientation should be the same as $PlayOrientation when $DisplayPieces is greater than 1, or parking position is not 0)
             $BulletNavigatorOptions: {                          //[Optional] Options to specify and enable navigator or not
                 $Class: $JssorBulletNavigator$,                 //[Required] Class to create navigator instance
                 $ChanceToShow: 2,                               //[Required] 0 Never, 1 Mouse Over, 2 Always
                 $ActionMode: 1,                                 //[Optional] 0 None, 1 act by click, 2 act by mouse hover, 3 both, default value is 1
                 $AutoCenter: 1,                                 //[Optional] Auto center navigator in parent container, 0 None, 1 Horizontal, 2 Vertical, 3 Both, default value is 0
                 $Steps: 1,                                      //[Optional] Steps to go for each navigation request, default value is 1
                 $Lanes: 1,                                      //[Optional] Specify lanes to arrange items, default value is 1
                 $SpacingX: 0,                                   //[Optional] Horizontal space between each item in pixel, default value is 0
                 $SpacingY: 0,                                   //[Optional] Vertical space between each item in pixel, default value is 0
                 $Orientation: 1                                 //[Optional] The orientation of the navigator, 1 horizontal, 2 vertical, default value is 1
             }
         };
         jssor_slider1 = new $JssorSlider$("sliderGroup_container", options);
        $('#slider1_container').hide();
     	$('#sliderGroup_container').show();

  });
}
 //Caursol widget  back clicks
    $('#widgetimageIcon').click(function() {
	  closeCustomWidget()
    });
    $('#widgetbackIcon').click(function() {
	   closeCustomWidget()
    });
    $('#widgetbackIconText').click(function() {
    	closeCustomWidget()
    });
    function closeCustomWidget()
    {
        $('#widgetCarousel').hide('slide', {direction: 'down'},  'slow');
        if($('#sliderGroup_container'))
    	{
        	$('#sliderGroup_container').remove();
        	$('#navigator').remove();
    	}
      

    }
    function setSliderWidth(contentDiv,content)
    {
    	// Set all store details
       	contentDiv.css('height',$( window ).height()-48-2);
    	content.css('height',$( window ).height()-48-20-4);
    	
    	contentDiv.css('width',$( window ).width()-2);
		content.css('width',$( window ).width()-20-2);
    }
    $( window ).resize(function() {
   	
    	setImageSliderWidth();
    });
    function setImageSliderWidth()
    {
    	$('#imgslider').width($(window).width());
    	$('#imgslider').css('margin-left','0px');
    	$('#slider').width($(window).width());
    	$('#slider ul').width($(window).width());
    	$('#slider ul li').width($(window).width());
    }

var loadKeySliderFlag=true;
var singleKeyView=false;
var keysArray= new Array();
function viewKeyJobInDetail(selectedIndx)
{
	if(singleKeyView)
	{
		$('#keySlider_container').empty();
		$('#keySlider_container').append('<div id="keySlidescover" u="slides"></div><div u="navigator"  id="keynavigator" class="jssorb03"><div u="prototype" id="keyprototype" class="prototype "> <NumberTemplate></NumberTemplate> </div></div>');
		block=$('<div class="item">');	
		block.append($('<p class="heading">').html(keysArray[selectedIndx][0]));
		block.append($('<p class="details">').html(keysArray[selectedIndx][1]));
		block.append($('</div>'));
		setSliderWidth($('#keySlider_container'),$('#keySlidescover'));
		$('#keySlidescover').append(block);
		$('.prototype').hide();
	}
	
	if(loadKeySliderFlag)
	{
		loadKeySliderFlag=false;
		setSliderWidth($('#keySlider_container'),$('#keySlidescover'));
		   jQuery(document).ready(function ($) {
		            var options = {
		                $DragOrientation: 1,                                //[Optional] Orientation to drag slide, 0 no drag, 1 horizental, 2 vertical, 3 either, default value is 1 (Note that the $DragOrientation should be the same as $PlayOrientation when $DisplayPieces is greater than 1, or parking position is not 0)
		                $BulletNavigatorOptions: {                          //[Optional] Options to specify and enable navigator or not
		                    $Class: $JssorBulletNavigator$,                 //[Required] Class to create navigator instance
		                    $ChanceToShow: 2,                               //[Required] 0 Never, 1 Mouse Over, 2 Always
		                    $ActionMode: 1,                                 //[Optional] 0 None, 1 act by click, 2 act by mouse hover, 3 both, default value is 1
		                    $AutoCenter: 1,                                 //[Optional] Auto center navigator in parent container, 0 None, 1 Horizontal, 2 Vertical, 3 Both, default value is 0
		                    $Steps: 1,                                      //[Optional] Steps to go for each navigation request, default value is 1
		                    $Lanes: 1,                                      //[Optional] Specify lanes to arrange items, default value is 1
		                    $SpacingX: 0,                                   //[Optional] Horizontal space between each item in pixel, default value is 0
		                    $SpacingY: 0,                                   //[Optional] Vertical space between each item in pixel, default value is 0
		                    $Orientation: 1                                 //[Optional] The orientation of the navigator, 1 horizontal, 2 vertical, default value is 1
		                }
		            };
		            jssor_slider1 = new $JssorSlider$("keySlider_container", options);

		     });
	}
	jssor_slider1.$PlayTo(selectedIndx); 
	$('#keySlider_container').show();
	$('#keysCarousel' ).show('slide', {direction: 'down'},  'slow');
}


var keyPageNum;
var noOfKeys;
var totalKeys;
var keyTimer;
var moreKeysFlag;


function getWeblinks(){
	
var weblinkpms={mallId: ongoorgid()}
	
	ongoAjaxRequestAsync("POST",'/Users/getWeblinks', weblinkpms, function(weblinkData){
		if(weblinkData.statusCode == '200'){
			$('#weblinks').show();
			var weblinks = weblinkData.weblinks;
			var wlblock = $('#weblinks').find('div.list-group');
			wlblock.html('');
			$.each(weblinks, function(idx){
				var weblink = weblinks[idx];
			//	var wlblock = $('<h4 class="panel-title panel panel-default">');
				//wlblock.append($('<a data-toggle="collapse" class="panel-heading"  data-parent="#accordion" href="#collapseFive">').html(weblink.name))
			//	$('#weblinksTabs').append(wlblock);
				
			//	wlblock.click(function(){
			//	createCookie('weblinkName', weblink.name);
			//	createCookie('weblinkUrl', weblink.url);
			//	window.location.href = relativePathToHome+'weblink.html';
			//	});
			wlblock.append($('<a href="javascript:void(0)" id="sc_'+weblink.id+'" class="list-group-item">')
						.html(weblink.name).click(function(){
							createCookie('weblinkName', weblink.name);
							createCookie('weblinkUrl', weblink.url);
					window.location.href = relativePathToHome+'weblink.html';
				}))
			
			});
		}
	}, null, false)
	
}

function checkKeysCount()
{
  if(totalKeys>12)
  {
	  totalKeys=totalKeys-12;
  	$('#keysLoadmore').css('display','block');
  	moreKeysFlag=true;
  }
  else
  {	
  		$('#keysLoadmore').css('display','none');
  		moreKeysFlag=false;
  }
}
/*function isScrolledIntoView(elem) {
    var docViewTop = $(window).scrollTop();
    var docViewBottom = docViewTop + $(window).height();
    var elemTop = $(elem).offset().top-50;
    var elemBottom = elemTop + $(elem).height();

    return ((elemBottom >= docViewTop) && (elemTop <= docViewBottom) && (elemBottom <= docViewBottom) && (elemTop >= docViewTop));

}*/


$(window).scroll(function () {
	
    clearTimeout(keyTimer);
    keyTimer = setTimeout(function() {
    if(moreKeysFlag)
    {	
        if (isScrolledIntoView($('#keysLoadmore'))) {
        	keyPageNum++;
        	checkKeysCount();
            addongoBuildKeys(keyPageNum,noOfKeys);
            return false;
        }
    }
    }, 50);
});

function addongoBuildKeys(keyPageNum,noOfKeys){
    var pms = {
			type : 'Keys',
			pageNumber:keyPageNum,
			pageSize:noOfKeys
	}

    var keyBlockId = 'keysBlock';
	ongoGetMastersAsync(pms, function(keys){
		
		/*for each key names d.kn array
		 append block with Key name and value.
		*/
		
		if(keys.jobs.length>10)
		{
		  singleKeyView=true;
		}
		var keyjobcount=0;
		$.each(keys.jobs, function(idx){
            var job = keys.jobs[idx];
			var block = $('<div class="block">');
			block.append($('<p class="heading">').html(job['Name']));
			block.append($('<p class="details">').html(job['Description']));
			block.append($('<p class="viewmore"  onClick="viewKeyJobInDetail('+keyjobcount+')" >').html("View more"));
			block.append($('<div class="clearfix"></div>'));
			$('#'+keyBlockId).append(block);
			keyjobcount=keyjobcount+1;
			keysArray[idx]=[job['Name'],job['Description']];
			// adding keys to JSSOR Slider
			if(!singleKeyView)
			{
				block=$('<div class="item">');	
				block.append($('<p class="heading">').html(job['Name']));
				block.append($('<p class="details">').html(job['Description']));
				block.append($('</div>'));
				$('#keySlidescover').append(block);
			}
			
			
			
		}); 
		
		/*if(keys.jobs.length == 0){
			var block = $('<div class="block rb">');
			
			block.append($('<p class="heading">').html('No keys found.'))
			
			$('#'+keyBlockId).append(block)
			
		}*/

	});
				
}



function erasesession(){
	eraseCookie('sremail')
	eraseCookie('loginorg')
	eraseCookie('loginid')
	eraseCookie('loginemail')	
	eraseCookie('loginroleName')	
}
function logout(redirect){
	erasesession()
	if(redirect){
		window.location.href = 'index.html';
	}
		
}


function ongoIsUserLoggedIn(){
	var issession= readCookie('loginid') != null && readCookie('loginid') != 'undefined'
	 && readCookie('loginroleName') == 'buyer' && readCookie('loginroleName') != 'undefined';
	 
	if(readCookie('loginroleName') == 'seller'){
		window.location.href='sellerorders.html';
	}
	return issession;
}

function ongobuildusermenu(){	
	
	if(ongoIsUserLoggedIn()){	
		$('#usermenu').html('');
		var profile = '<li><a href="myaccount.html">Profile</a></li>';	
		var mycart = '<li class="hidden-xs"><a href="cart.html">My Cart</a></li>';
		var mycheckout='<li class="hidden-xs"><a href="checkout.html">Checkout</a></li>';
        var signout = $('<li><a href="javascript:void(0)">Signout</a></li>').click(function(){
        	logout(true)
        });
        $('#usermenu').append(profile).append(mycart).append(mycheckout).append(signout);
	}else{
		$("#mycart").html('');
		erasesession(false)
		
	}
	ongoAjaxRequestAsync("GET",'/eKisan/buyer/categories','', function(res){
		console.log(res);
		var home = '<li><a href="index.html">Home</a></li>';	
		var allPrs = '<li><a href="product.html">All Products</a></li>';	
		$('#categoryList').append(home);
		$('#categoryList').append(allPrs);
		$.each(res.data, function(idx){
	    var cat = res.data[idx];
        var li = $('<li><a href="javascript:void(0)">'+cat.name+'</a></li>').click(function(){
        	getProductsByCategory(cat.id)
        });
        $('#categoryList').append(li);
			});
		});
}

function getProductsByCategory(catId){
window.location.href='product.html?catId='+catId;	
}

function loadProducts(){
//alert(new URL(location.href).searchParams.get("catId"));
	var catId = new URL(location.href).searchParams.get("catId");
	var url;
	if(catId!=null && catId!=undefined && catId!="")
	    url = '/eKisan/buyer/productlist?categoryId='+catId;
	else
	    url = '/eKisan/buyer/productlist?categoryId=';

	ongoAjaxRequestAsync("GET",url,'', function(res){
		console.log(res);
		$.each(res.data, function(idx){
		var product = res.data[idx];
		console.log("prod"+product.name);
        var childli = '<li><figure><a class="aa-product-img" href="#"><img src="img/women/girl-1.png" alt="polo shirt img"></a>'+
         '<a class="aa-add-card-btn" id="addToCart" onclick="addToCart('+product.id+')"><span class="fa fa-shopping-cart"></span>Add To Cart</a>'+
         '<figcaption>'+
         '<h4 class="aa-product-title"><a href="#">'+product.name+'</a></h4>'+
          '<span class="aa-product-price">'+product.price+'</span>'+
          '</figcaption>'+
          '</figure> </li>';
          $('#allProductsList').append(childli);
			});
		});
}
function findCartByUserIDAndProductId(userId,productId){
window.location.href='product.html?userId='+userId+'&productId';
}

function addToCart(productId){
var userId = readCookie('loginid');
  alert(productId+''+userId);
var cartObj = {};
var url='/eKisan/buyer/cart';
cartObj.userId=userId;
cartObj.productId=productId;
cartObj.quantity=1;

ongoAjaxRequestAsync("POST",url,cartObj, function(res){

        if(res.success ==true && res.statusCode == '200'){
			$('#cartCount').val(res.data.length);
		}else{
			getToast(res.message);
		}
       },null, false);
       return false;
}


function consolelogfunc(str){
	if( consolelog ){
		console.log(str)
	}else{
		//NOT PRINTING CONSOLE LOGS
	}	
	
}

function ongoAfterLogin(conLoginres){

	createCookie('loginid', conLoginres.data['id'])
	createCookie('loginemail', conLoginres.data['email'])
	createCookie('loginname', conLoginres.data['name'])
	createCookie('loginroleName', conLoginres.data['roleName'])
	createCookie('conLoginres', JSON.stringify(conLoginres.data))
	
	window.location.href='index.html';
	
}

function ongologin(form){
	var form = $('#signinform')
	var pms = {};
	pms.email = form.find('input[name=email]').val()
	pms.password = form.find('input[name=password]').val()
	
	ongoAjaxRequestAsync("POST",'/eKisan/login', pms, function(res){
		
		if(res.statusCode == '200'){
			ongoAfterLogin(res)
		}else{
			if(pms.email == ''){
				$('#signinEmailId').focus();
				getToast('Please enter email address');
				return false;
			}else{
				$('#signinEmailId').focus();
				 var atpos = pms.email.indexOf("@");
		         var dotpos = pms.email.lastIndexOf(".");
		         if (atpos<1 || dotpos<atpos+2 || dotpos+2>=pms.email.length) {
		        	 getToast('Please enter valid email address');
		        	 return false;
		         }
			}
			if(pms.password == ''){
				$('#signinPassword').focus();
				getToast('please enter password');
				return false;
			}
			getToast(res.message);
		}
	}, null, false)	

	
	return false;
}

function ongoreg(form){
	var form = $('#regform')
	var pms = {};
	var signupUserEmailId = $("#signupUserEmailId").val();
	var signupMobileId = $("#signupMobileId").val();
	pms.firstName = form.find('input[name=firstName]').val()
	pms.lastName = form.find('input[name=lastName]').val()
	pms.mobileNumber = form.find('input[name=mobileNumber]').val()
	pms.userEmailId = form.find('input[name=userEmailId]').val()
	pms.password = form.find('input[name=password]').val()
	pms.isLoginWithFB=false;
	
	if(pms.firstName == ''){
		$('#signupFirstNameId').focus();
		getToast('Your First name should be at least 3 characters');
		return false;
	}
	if(pms.firstName.length <= 2){
		$('#signupFirstNameId').focus();
		getToast('Your First name should be at least 3 characters');
		return false;
	}
	if(pms.lastName == ''){
		$('#signupLastNameId').focus();
		getToast('Your Last name should be at least 3 characters');
		return false;
	}
	if(pms.lastName.length <= 2){
		$('#signupLastNameId').focus();
		getToast('Your Last name should be at least 3 characters');
		return false;
	}
	if(signupMobileId == ''){
		$('#signupMobileId').focus();
		getToast('Your mobilenumber is invalid');
		return false;
	}
	if(signupMobileId.length <= 9){
		$('#signupMobileId').focus();
		getToast('Your mobilenumber is invalid');
		return false;
	}
	if(signupUserEmailId == ''){
		$('#signupUserEmailId').focus();
		getToast('Your Email format is invalid');
		return false;
	}else{
		$('#signupUserEmailId').focus();
		 var atpos = signupUserEmailId.indexOf("@");
         var dotpos = signupUserEmailId.lastIndexOf(".");
         if (atpos<1 || dotpos<atpos+2 || dotpos+2>=signupUserEmailId.length) {
        	 getToast('Your Email format is invalid');
        	 return false;
         }
	}
	if(pms.password == ''){
		$('#signUpPasswordId').focus();
		getToast('Your password length should be 6 to 20');
		return false;
	}
	if(pms.password.length <= 5){
		$('#signUpPasswordId').focus();
		getToast('Your password length should be 6 to 20');
		return false;
	}

	ongoreg1(pms)
	return false;
}

function ongoreg1(pms){
	pms.orgId = ongoorgid();
	pms.dt='DEVICES';
	ongoAjaxRequestAsync("POST",'/MobileAPIs/regAndloyaltyAPI', pms, function(res){
		if(res.statusCode == '200'){
			ongoAfterLogin(res)
		}else{
			getToast(res.message);
		}
		//console.log(res)
	}, null, false)	
	
	return false;
}

function ongoUpdateUser(form){
	var form = $('#updateUserform')
	var pms = {};
	pms.email = form.find('input[name=email]').val()
	pms.password = form.find('input[name=password]').val()
	pms.firstName = form.find('input[name=firstName]').val()
	pms.lastName = form.find('input[name=lastName]').val()
	
	pms.mobileNo = form.find('input[name=mobileNo]').val()
	pms.city = form.find('input[name=city]').val()
	pms.state = form.find('input[name=state]').val()
	pms.address = form.find('input[name=address]').val()
	pms.country = $('#countrynames').val()
	pms.userImagePath = form.find('input[name=userImagePath]').val()
	pms.userBannerPath = form.find('input[name=userBannerPath]').val()
	pms.gender = $('#gender').val()
	pms.orgId = form.find('input[name=orgId]').val()
	
	pms.isLoginWithFB=false;

	ongoUpdateUser1(pms)
	
	return false;
}
function ongoUpdateUser1(pms){
	pms.orgId = ongoorgid();
	pms.dt='DEVICES';
	ongoAjaxRequestAsync("POST",'/MobileAPIs/updateUserDetails', pms, function(res){
		if(res.statusCode == '200'){
			createCookie('loginorg', res.orgId)
			createCookie('loginid', res.UserId)
			createCookie('loginemail', res.emailId)
			createCookie('conLoginres', JSON.stringify(res))
			getToast(res.message);
			window.setTimeout('location.reload()', 1000);
		}else{
			getToast(res.message);
			/*alert(res.message)*/
		}
		//console.log(res)
	}, null, false)	
	
	return false;
}
function ongochangepassword(form){
	var form = $('#updatepassword')
	var pms = {};
	pms.orgId = form.find('input[name=orgId]').val()
	pms.email = form.find('input[name=email]').val()
	pms.cPassword = form.find('input[name=cPassword]').val()
	pms.nPassword = form.find('input[name=nPassword]').val()
	pms.nrPassword = form.find('input[name=nrPassword]').val()
	if(pms.cPassword == ''){
		$('#cPassword').focus();
		getToast('Enter Current password');
		return false;
	}
	if(pms.nPassword == ''){
		$('#nPassword').focus();
		getToast('Enter New password');
		return false;
	}
	if(pms.nPassword != pms.nrPassword){
		$('#nPassword').focus();
		getToast('Password does not match');
		return false;
	}
	ongochangepassword1(pms)
	return false;
}

function ongochangepassword1(pms){
	pms.dt='DEVICES';
	ongoAjaxRequestAsync("POST",'/MobileAPIs/changePassword', pms, function(d){
		var res = d.myHashMap
		if(res.statusCode == '200'){
			window.location.href = '../profile/profile.html';
		}else{
			/*alert(res.message)*/
			getToast(res.message);
		}
		//console.log(res)
	}, null, false)	
	
	return false;
}
function ongoforgotpassword(form){
	var form = $('#forgotpassword')
	var pms = {};
	
	pms.email = form.find('input[name=email]').val()
	if(pms.email == ''){
		$('#forgotpasswordEmailId').focus();
		getToast('Please enter email address');
		return false;
	}else{
		$('#forgotpasswordEmailId').focus();
		 var atpos = pms.email.indexOf("@");
         var dotpos = pms.email.lastIndexOf(".");
         if (atpos<1 || dotpos<atpos+2 || dotpos+2>=pms.email.length) {
        	 getToast('Please make sure if email is correct.');
        	 return false;
         }
	}
	ongoforgotpassword1(pms)
	return false;
}

function ongoforgotpassword1(pms){
	pms.orgId = ongoorgid();
	pms.dt='DEVICES';
	ongoAjaxRequestAsync("POST",'/MobileAPIs/forgotpwd', pms, function(res){
		if(res.statusCode == '200'){
			window.location.href = '../profile/signin.html';
		}else{
			/*alert(res.message)*/
			getToast('Please make sure if email is correct')
		}
		//console.log(res)
	}, null, false)	
	
	return false;
}
function ongoRegWithFBCallback(){
	var pms = {};
	pms.userEmailId = GetURLParameter('email')
	pms.password = '321'
	pms.firstName = GetURLParameter('first_name')
	pms.lastName = GetURLParameter('last_name')
	pms.isLoginWithFB = true;
	
	ongoreg1(pms)
	
	return false;
}

function GetURLParameter(sParam)
{

    var sPageURL = window.location.search.substring(1);

    var sURLVariables = sPageURL.split('&');

    for (var i = 0; i < sURLVariables.length; i++)

    {

        var sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] == sParam)

        {

            return sParameterName[1];

        }

    }

}
function ongoSetLogo(){
	// Set all store details
	var d = jQuery.parseJSON(readCookie('data'));
	$('#screenName').html(d.n);
	var conLoginres = jQuery.parseJSON(readCookie('conLoginres'));
	
	// Set logo 
	if(d.l)
	$('#imageIcon').attr('src',d.l);
    else
   $('#imageIcon').attr('src','images/mobile/business.png');
	
	$('#imageIcon').click(function(){
		window.location.href = relativePathToHome+'index.html';
	}).css({'cursor': 'pointer'})

	    erasesession(false)
		var forgot = '<li><a href="'+relativePathToHome+'profile/forgotpassword.html">Forgot password?</a></li>';
        $('#usermenu').append(forgot);
	
}
function forgotpassword(){
	// Set all store details
	var d = jQuery.parseJSON(readCookie('data'));
	$('#screenName').html(d.n);
	var conLoginres = jQuery.parseJSON(readCookie('conLoginres'));
	
	// Set logo 
	if(d.l)
	$('#imageIcon').attr('src',d.l);
    else
    $('#imageIcon').attr('src','images/mobile/business.png');
	
	$('#imageIcon').click(function(){
		window.location.href = relativePathToHome+'index.html';
	}).css({'cursor': 'pointer'})

	ongobuildusermenu();
	
}


function ongoUpdateProfile(){
	// Set all store details
	var d = jQuery.parseJSON(readCookie('data'));
	$('#screenName').html(d.n);
	var conLoginres = jQuery.parseJSON(readCookie('conLoginres'));
	// Set logo 
	if(d.l)
	$('#imageIcon').attr('src',d.l);
    else
    $('#imageIcon').attr('src','images/mobile/business.png');
	
	$('#imageIcon').click(function(){
		window.location.href = relativePathToHome+'index.html';
	}).css({'cursor': 'pointer'})
	
	if(conLoginres.userImagePath)
	$('#circlePhoto').attr('src',conLoginres.userImagePath);
    else
    $('#circlePhoto').attr('src','../images/6.jpg');
	
	$('#email').val(conLoginres.emailId);
	$('#firstName').val(conLoginres.firstName);
	$('#lastName').val(conLoginres.lastName);
	$('#mobileNo').val(conLoginres.mobile);
	$('#city').val(conLoginres.city);
	$('#state').val(conLoginres.state);
	$('#country').val(conLoginres.country);
	$('#address').val(conLoginres.address);
	$('#userImagePath').val(conLoginres.userImagePath);
	$('#userBannerPath').val(conLoginres.userBannerPath);
	$('#gender').val(conLoginres.gender);
	$('#orgId').val(conLoginres.orgId);
	if(conLoginres.gender==0)
	{
		$('#genderName').val("Male");
	}
	else
	{
		$('#genderName').val("Female");		
	}
		
    populateCountries("countrynames");
    ongobuildusermenu();
}
// Profile page change Picture Functionality
var changePictureFlag=true;
/*$('.changePicture').click(function(){
	
	if(changePictureFlag)
	{
	 $('#changePicture_file').click();
	 changePictureFlag=false;
	}
	else
	{
	 changePictureFlag=true;
	 ongoUpdateUser('');
	}	
});*/
$('#changepicture_text').click(function(){
	 $('#changePicture_file').click();
});
$('#changepicture_text_save').click(function(){
	ongoUpdateUser('');
	 $('#changepicture_text').show();
	 $('#changepicture_text_save').hide();
});

$('#changePicture_file').change(function (evt) {
	   
	    	    var file = this.files[0];
	    	            var formData = new FormData($('#uploadFileForm')[0]);
	    	            $.ajax({
	    	                url: ongosettings.apiurl+'/Services/uploadFileFromAndroidToServer',  //server script to process data
	    	                type: 'POST',
	    	                xhr: function() {  // custom xhr
	    	                    myXhr = $.ajaxSettings.xhr();
	    	                    if(myXhr.upload){ // if upload property exists
	    	                    }
	    	                    console.log(myXhr)
	    	                    return myXhr;
	    	                },
	    	                //Ajax events
	    	                success: completeHandler = function(data) {
	    	                	if(data.statusCode == '200'){
	    	                		$('#circlePhoto').attr('src', data.filePath);
	    	                		$('#userImagePath').val(data.filePath);
	    	                	}
	    	                	else{
	    	                	/*alert(data.response);*/	
	    	                	getToast(data.response);
	    	                	}
	    	                },
	    	                error: errorHandler = function(data) {
	    	                	/*alert(data.response);*/
	    	                	getToast(data.response);
	    	                },
	    	                // Form data
	    	                data: formData,
	    	                //Options to tell JQuery not to process data or worry about content-type
	    	                cache: false,
	    	                contentType: false,
	    	                processData: false
	    	            });
	    	       
	    //$('#changepicture_text').html('Save Changes');
	    $('#changepicture_text').hide();
	    $('#changepicture_text_save').show();

});

function updatepassword(){
	// Set all store details
	var d = jQuery.parseJSON(readCookie('data'));
	$('#screenName').html(d.n);
	var conLoginres = jQuery.parseJSON(readCookie('conLoginres'));
	// Set logo 
	if(d.l)
	$('#imageIcon').attr('src',d.l);
    else
    $('#imageIcon').attr('src','images/mobile/business.png');
	
	$('#imageIcon').click(function(){
		window.location.href = relativePathToHome+'index.html';
	}).css({'cursor': 'pointer'})
	
	$('#email').val(conLoginres.emailId);
	$('#orgId').val(conLoginres.orgId);
    ongobuildusermenu();
}


function ongoGetCartUser(){
	var loginid = readCookie('loginid')
	return loginid == null ? ongoorgid() : loginid;
}

function ongoGetLoginUser(){
	var loginid = readCookie('loginid')
	return loginid;
}

function ongoGetCart(){
	var d = jQuery.parseJSON(readCookie('data'));
	var cart = readCookie('cart') == null ? {} : jQuery.parseJSON(readCookie('cart'));
	if(cart == null || cart.orgid == null || cart.orgid != ongoorgid() || cart.userid == null || cart.userid != ongoGetCartUser()){
		cart = {};
		cart.orgid = ongoorgid();
		cart.userid = ongoGetCartUser();
		cart.items = {};
	}	
	return cart;
}


function ongoSetCartSize(){
	var cnt = 0;
	for(var i in ongoGetCart().items){
		cnt++;
	}
	if($('.cart').find('.cartCount').length > 0){
		$('.cart').find('.cartCount').text(cnt)
	}
}
function addItemToCart(itemid, name, icon, mrp){
	$('#addToCartP_'+itemid).remove();
	var cart = $('<p class="addToCart" id="addToCartP_'+itemid+'" onclick="removeItemFromCart(\''+itemid+'\',\''+name+'\',\''+icon+'\',\''+mrp+'\')">'+
			   '<button id="addToCartButton_'+itemid+'" class="btn btn-red small full addedBtn">Added</button></p>');
	//var orgData = jQuery.parseJSON(readCookie('data'));
	$('#addToCartDiv_'+itemid).append(cart);
	$('.addedBtn').css('background',colortheme);
	   ongoaddtocart(itemid, name, icon, 1, mrp)
}
function removeItemFromCart(itemid, name, icon, mrp){
	$('#addToCartP_'+itemid).remove();
	var cart = $('<p class="addToCart" id="addToCartP_'+itemid+'" onclick="addItemToCart(\''+itemid+'\',\''+name+'\',\''+icon+'\',\''+mrp+'\')">'+
			   '<button id="addToCartButton_'+itemid+'" class="btn btn-dark small full cartBtn"> Add to cart</button></p>');
	$('#addToCartDiv_'+itemid).append(cart);
	ongoModifyCartItem(itemid, false)
}

function ongoaddtocart(itemid, name, icon, quantity, mrp){
	//console.log(itemid, name, icon, quantity, mrp);
	var cart = ongoGetCart();
	var item = {};
	item.name = name;
	item.icon = icon;
	item.quantity = quantity;
	item.mrp = mrp ? mrp : 0;
	
	/*if(!ongoIsUserLoggedIn()){
		if(confirm('Please login to add an item to cart.')){
			item.itemid = itemid;
			item.orgid = cart.orgid;
			createCookie('loginaddtocart', JSON.stringify(item))
			window.location.href = relativePathToHome+'profile/signin.html';
		}
		return;
	}*/
	
	cart.items[itemid] = item;
	
	createCookie('cart', JSON.stringify(cart))
	ongoSetCartSize()
	getToast(name + ' added to cart.')
	//alert(name + ' added to cart.')
}

function validateCartForm(){
	var name = $('#consumerName').val();
	var email = $('#consumerEmail').val();
	var address = $('#consumerAddress1').val();
	var ph = $('#consumerPhoneNumber').val();
	
	if(name == ''){
		//alert('Name is required');
		//document.getElementById("consumerName").style.border = "1px solid #bf263a";
		$('#consumerName').attr('placeholder','Name is required');
		$('#consumerName').focus();
		return false;
	}
	if(email == '') {
		//alert('Email is required');
		$('#consumerEmail').attr('placeholder','Email is required');
		$('#consumerEmail').focus();
		return false;
	}else if(!validateEmail(email)){
		//alert('Enter valid email');
		$('#consumerEmail').val('');
		$('#consumerEmail').attr('placeholder','Enter valid email');
		$('#consumerEmail').focus();
		return false;
	}
	if(address == ''){
		//alert('Address is required');
		$('#consumerAddress1').attr('placeholder','Address is required');
		$('#consumerAddress1').focus();
		return false;
	}
	if(ph == ''){
		//alert('Phone Number is required');
		$('#consumerPhoneNumber').attr('placeholder','Phone Number is required');
		$('#consumerPhoneNumber').focus();
		return false;
	}else if(!validatePhoneNumber(ph)){
		//alert('Enter valid phone number');
		$('#consumerPhoneNumber').val('');
		$('#consumerPhoneNumber').attr('placeholder','Enter valid phone number');
		$('#consumerPhoneNumber').focus();
		return false;
	}
	$('#consumerDetails').modal('hide');
	ongoSubmitCart();
	
}
function validateEmail(email) {
	var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	if (filter.test(email)) {
		return true;
	}
	else {
		return false;
	}
}
function validatePhoneNumber(ph) {
	var filter = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
	if (filter.test(ph)) {
		return true;
	}
	else {
		return false;
	}
}
function ongoSubmitCart1(){
	var cartCount = parseInt($('#cartCount').html());
	if(cartCount == 0){
		getToast('Your cart is empty')
	}else{
		$('#consumerDetails').modal('show');
	}
	
	/*if(!ongoIsUserLoggedIn()){
		$('#consumerDetails').modal('show');
		return;
	}else if(confirm('Are you sure you want to submit cart?')){
		ongoSubmitCart();
	}*/
}


var proceed = true;
function ongoSubmitCart(){
	if(proceed ){
		proceed = false;
		var cart = ongoGetCart();
		var t = cart.items;
		var itemExists = false;
		var cartSubmitData = {};
		cartSubmitData.type = 'PlaceOrder';
		cartSubmitData.dt = 'CAMPAIGNS';
		cartSubmitData.category = 'Services';
		cartSubmitData.userId= ongoorgid()	
		cartSubmitData.consumerEmail = readCookie('loginemail') != null && readCookie('loginemail') != '' ? readCookie('loginemail') : $('#consumerEmail').val();
		
		var items = {};
		items.list = [];
		
		var itemsInfo = {};
		
		//itemsInfo.Name = 'PO_' + cartSubmitData.userId + '_'+new Date().toISOString();
		//itemsInfo.ItemCode = 'PO_'+ cartSubmitData.userId + '_' + new Date().getTime();
		//itemsInfo.Address = 'HNO,Area,Locality,City,State,Country';
		//itemsInfo.Contact_Number = '99999999999';
		
		itemsInfo.Name = $('#consumerName').val();
		itemsInfo.ItemCode = 'PO_'+ cartSubmitData.userId + '_' + new Date().getTime();
		itemsInfo.Address = $('#consumerAddress1').val()+','+$('#consumerAddress2').val();
		itemsInfo.Contact_Number = $('#consumerPhoneNumber').val();
		
		itemsInfo.OrderItemId = '';
		itemsInfo.OrderItemQuantity = '';
		itemsInfo.OrderItemName = '';
		itemsInfo.OrderItemSubTotal = '';
		itemsInfo.OrderItemMRP = '';
		
		var totalPrice = 0;	
		var addInfo = function(input){
			if(input == ''){
				return '';
			}
			return input + '|';
		}
		for(var i in t){
			var item = t[i]
			itemExists = true;
			var itotal = parseFloat(''+item.mrp) * parseInt(''+item.quantity);
			
			itemsInfo.OrderItemId = addInfo(itemsInfo.OrderItemId) + (i+'`'+i);
			itemsInfo.OrderItemQuantity = addInfo(itemsInfo.OrderItemQuantity) + (item.quantity+'`'+i);
			itemsInfo.OrderItemName = addInfo(itemsInfo.OrderItemName) + (item.name.replace('`', ' ').replace('|', ' ')+'`'+i);
			itemsInfo.OrderItemSubTotal = addInfo(itemsInfo.OrderItemSubTotal) + (itotal+'`'+i);
			itemsInfo.OrderItemMRP = addInfo(itemsInfo.OrderItemMRP) + (item.mrp+'`'+i);
			
			totalPrice += itotal;		
		}
		
		itemsInfo.Total = totalPrice;
		
		items.list[0] = itemsInfo;
		
		cartSubmitData.json = JSON.stringify(items);
		
		ongoAjaxRequestAsync("POST",'/MobileAPIs/postedJobs', cartSubmitData, function(d){
			var res = d.myHashMap
			if(res.statusCode == '200'){
				eraseCookie('cart')
				//alert('Your cart submitted successfully.')
				window.location.href = relativePathToHome+'index.html';
			}else{
				getToast(res.message);
				/*alert(res.message)*/
			}
		}, null, false)	
	
	}
}

function ongoModifyCartItem(itemid, flg){
	var cart = ongoGetCart();
	var t = cart.items;
	var deleteItemId = null;
	for(var i in t){
		var item = t[i]
		if(i == itemid){			
			var qnty = item.quantity;
			if(flg){
				qnty++;
			}else{
				qnty--;
			}
			
			if(qnty <= 0){
				deleteItemId = i;
			}else{
				item.quantity = qnty;
			}
		}		
	}
	
	if(deleteItemId != null){
		delete cart.items[deleteItemId]
	}
	
	createCookie('cart', JSON.stringify(cart))
	ongoSetCartSize()
	
	ongoBuildCart1()
	
}



function ongoBuildCart1(){
	
	var cart = ongoGetCart();
	var t = ongoGetCart().items;
	var totalPrice = 0;
	
	var c = $('.itemsBlock').html('')
	
	var d = jQuery.parseJSON(readCookie('data'));
	var conLoginres = jQuery.parseJSON(readCookie('conLoginres'));
	if(conLoginres != null && conLoginres != '' && typeof conLoginres != 'undefined'){
		$('#consumerName').val(conLoginres.firstName+' '+conLoginres.lastName);
		$('#consumerEmail').val(conLoginres.emailId);
		$('#consumerAddress1').val(conLoginres.address);
		var city = conLoginres.city != null ? conLoginres.city : '';
		var state = conLoginres.state != null ? ' , '+conLoginres.state : '';
		var country = conLoginres.country != null ? ' , '+conLoginres.country : '';
		
		$('#consumerAddress2').val(city+''+state+''+country);
		$('#consumerPhoneNumber').val(conLoginres.mobile);
	}
	
	for(var i in t){
		var item = t[i]
		 var citem = $('<div class="addtocartBlock" itemid="'+i+'">');
	   	 
	   	 var icon = $('<div class="cartItemImgBlock">').append(
	   			 '<img class="cartItemImg" src="'+item.icon+'" href="javascript:void(0)" width="90%" height="90%" >');
	     
	     var nameprice = $('<div class="cartItemDetailsTopbar">')
	     	.append($('<a class="blackfont">').text(item.name))
	     	if(d.currencyType == 'INR'){
	     		nameprice.append($('<a class="redfont" </a>').html('<i class="fa fa-inr"></i>&nbsp;'+item.mrp));	     		
	     	}else{
	     		nameprice.append($('<a class="redfont" </a>').html('<i class="fa fa-dollar"></i>&nbsp;'+item.mrp));
            }
	
	     var qnty = '<div class="cartItemDetailsBottombar">'+
	      	 '<a class="blackfont">Quantity</a>'+
         '<a class="redfont">'+item.quantity+'</a></div>';	
	     var itotal = parseFloat(''+item.mrp) * parseInt(''+item.quantity);
	     if(d.currencyType == 'INR'){
	    	 var total = '<div class="cartItemDetailsRightBottombar"><a class="blackfont textallignright">Total</a><a class="redfont textallignright"><i class="fa fa-inr"></i>&nbsp;'+itotal+' /-</a></div>';
	     }else{
	    	 var total = '<div class="cartItemDetailsRightBottombar"><a class="blackfont textallignright">Total</a><a class="redfont textallignright"><i class="fa fa-dollar"></i>&nbsp;'+itotal+' /-</a></div>';
	     }
	     var btn1 = $('<button class="btn btn-primary plusBtn" itemid="'+i+'">+</button>').click(function(){
	    	 ongoModifyCartItem($(this).attr('itemid'), true)
	     });
	     
	     var btn2 = $('<button class="btn btn-primary minusBtn" itemid="'+i+'">-</button>').click(function(){
	    	 ongoModifyCartItem($(this).attr('itemid'), false)
	     });
	 
	     
	     citem.append(icon).append(nameprice).append(qnty).append(total).append(btn1).append(btn2)
	     totalPrice += itotal;

	     c.append(citem)
	}
	
	
	if(d.currencyType == 'INR'){
		$('.totalvalueBlock').html('=&nbsp<i class="fa fa-inr"></i>&nbsp;'+totalPrice);
	}
	else{
		$('.totalvalueBlock').html('=&nbsp<i class="fa fa-dollar"></i>&nbsp;'+totalPrice);
	}
	
	getAppThemeColor(colortheme)
		
}


function ongoBuildCart(){
	ongobuildusermenu();
	ongoBuildCart1();
}

function ongoBuildSingleItem(){
	// Build left menu
	ongoBuildMenu();
	
	var cs = jQuery.parseJSON(readCookie('cs'));
	
	var prdjt = readCookie('prdjt');
	var jid = readCookie('jid');
	var mt = readCookie('mt');	

	var pms = {
		jobId : jid	
	}
    
	$('#productView').html('')
			
	$('#reviewSingleItemTab').find('a').click(function(){
		if($('.ratingBtn').css('display')=='block')
		{
			$('#writeReviewButton').addClass('half');
		}
		else
		{
			$('#writeReviewButton').removeClass('half');
		}		
		
		if(ongoGetLoginUser() != null){
		var pmsc = {};
	    pmsc.userId = ongoGetLoginUser();
        pmsc.jobId = jid;

        ongoAjaxRequestAsync("POST",'/jobs/getConsumerReview', pmsc, function(d){
			var res = d.myHashMap
			if(res.statusCode == '200'){
				var ratingType = 0;
				if(res.rating == 0.5){
					ratingType =  1
				}else if(res.rating == '1.0' || res.rating == '1'){
					ratingType =  2
				}else if(res.rating == '1.5'){
					ratingType =  3
				}else if(res.rating == '2.0' || res.rating == '2'){
					ratingType =  4
				}else if(res.rating == '2.5'){
					ratingType =  5	
				}else if(res.rating == '3.0' || res.rating == '3'){
					ratingType =  6
				}else if(res.rating == '3.5'){
					ratingType =  7
				}else if(res.rating == '4.0' || res.rating == '4'){
					ratingType =  8
				}else if(res.rating == '4.5'){
					ratingType =  9
				}else if(res.rating == '5.0' || res.rating == '5'){
					ratingType =  10
				}
				defineRate('userRating_'+ratingType,'');
				defineRateItem('userRatingItem_'+ratingType,'');
				$('#commentTextarea').text(res.comment);
				$('#commentId').val(res.commentId);
				//console.log(res);
			}
		}, null, false)	
	  }
		
	})
	ongoGetMastersAsync(pms, function(cats){
		$.each(cats.jobs, function(idx){
			var job = cats.jobs[idx];

			//$('meta[name=keywords]').attr('content', new_keywords);
			//$('meta[property="og:title"]').attr('content', job['Name']);
			
			/*$('#tagTitle').attr('content', job['Name']);
			$('#tagDesc').attr('content', job['Description']);
			$('#tagImage').attr('content', job['Image_URL']);
			$('#tagUrl').attr('content', job['Image_URL']);*/
			
			$('meta[property=og\\:title]').attr('content', job['Name']);
			$('meta[property=og\\:description]').attr('content', job['Description']);
			$('meta[property=og\\:image]').attr('content', job['Image_URL']);
			$('meta[property=og\\:url]').attr('content', job['Image_URL']);
			
			
			$('#singelItemscreenName').html(job['Name']);

			ongoJobReviews('reviewsBlock', job, ['firstReview'])
			$('#overallRating').html(job['overallRating']+' Overall Rating');
			
			var div1 = $('<div class="productImageBlock">')
			   .append('<a class="image"><img src="'+job['Image_URL']+'"></a>');
			
			var price = job['MRP'];
			   price = price == null || price == '' ? '0': price;
            
			var div2 = $('<div class="productDetailsBlock">')
			   .append('<div class="leftBlock pull-left">'+
				          '<p class="productName">'+job['Name']+'</p>'+
				          '<p class="productPrice"><span><i class="fa fa-inr"></i> '+job['MRP']+' </span></p>'+
				          '</div>'+
				          '<div class="clearfix"></div>')
		          if(parseInt(price)>0){
		        	  div2.append('<div class="rightBlock pull-right">'+
				       // '<img class="favouriteIcon"></img>'+
				        '</div>'+
				        '<div class="clearfix"></div>'+
				        '<div class="options" id="addToCartDiv_'+job['id']+'">'+
				        '<p  id="addToCartP_'+job['id']+'" onclick="addToCartSingleItem(\''+job['id']+'\',\''+job['Name']+'\',\''+job['Image_URL']+'\',\''+price+'\')"><button class="btn btn-dark small cartBtn"> Add to Cart</button></p></div>'+
				        '<div class="writeBtn" onclick="writeReview()"><i class="penIcon"></i>&nbsp;Write</div>');
				          
		          }

			
			var div3=$('<div class="productDetailsBlock">')
			.append('<p class="heading">'+"Description"+'</p>'+
					'<p class="description">'+job['Description']+'</p>'+
             '</div>');
			$('#productView').append(div1);
			$('#productView').append(div2);
			$('#productView').append(div3);
			var temp=0;
			$.each( job, function( key, val ) {
				temp++;
				if(key != 'ItemCode' && key != 'id' && key != 'Name' && key != 'createdById' && key != 'jobTypeId' && key != 'createdByFullName' && key != 'publicURL'
					&& key != 'ExpiredOn' && key != 'Quantity' && key != 'In_Stock' && key != 'guestUserId' && key != 'guestUserEmail' && key != 'Image_Name' && key != 'Image_URL'
					&& key != 'Attachments' && key != 'Additional_Details' && key != 'jobComments' && key != 'Current_Job_Status' && key != 'Category_Mall' && key != 'MRP' && key != 'Insights'
					&& key != 'Current_Job_StatusId' && key != 'Next_Seq_Nos' && key != 'CreatedSubJobs' && key != 'Next_Job_Statuses' && key != 'offersCount' && key != 'productsCount' && key != 'businessType'
					&& key != 'storeId' && key != 'CategoryType' && key != 'SubCategoryType' && key != 'P3rdCategory' && key != 'Description' && key != 'createdOn' && key != 'jobTypeName'
					&& key != 'hrsOfOperation' && key != 'totalReviews' && key != 'PackageName' && key != 'overallRating' && key != 'ServiceType' && key != 'Latitude' && key != 'Longitude'){
					var prop = $('<div  id="propertyId'+temp+'"  onclick="propertyClick('+temp+')" class="productPropertyDetailsBlock">')
							.append('<p class="propertyName">'+key+'</p>'+
									'<p id="propertyValueId'+temp+'" class="propertyValue">'+val+'</p>'+
								'</div>');
					$('#productProps').append(prop);
				}
			});
			
			var myArrayjobs=[job['Image_URL'],job['Name'],job['MRP'],job['Description']];
			var myArraydivs=['.image','#name','.productPrice','#desc'];

			for (var i = myArrayjobs.length - 1; i >= 0; i--) {
				if(!myArrayjobs[i])
				{
					displayNone(myArraydivs[i]);
				}
			};

			if(!parseInt(job['MRP']))
			{
				displayNone('.productPrice');
			}

		});
		if(isCartEnabled == false){
			$('.productView .productDetailsBlock .writeBtn').css('margin-top','0px');
			$('.cartBtn').hide();
		}
	});
	
}
var prevSelectedProp;
var prevSelectedPropValue;
function propertyClick(idx)
{
	if($('#propertyValueId'+idx).css('display')=='none')
	{
		$('#propertyValueId'+idx).fadeIn();
		$('#propertyId'+idx).addClass('white');
		
		if(prevSelectedProp)
		{
			prevSelectedProp.removeClass('white');
			prevSelectedPropValue.hide();
		}
		prevSelectedProp=$('#propertyId'+idx);
		prevSelectedPropValue=$('#propertyValueId'+idx);
	}	
	else
	{
		$('#propertyValueId'+idx).hide();
		$('#propertyId'+idx).removeClass('white');
		prevSelectedProp=undefined;
	}	
}
function addToCartSingleItem(itemid, name, icon, mrp)
{
	  /* var prnt = $(this).parent()
	   var itemid = jobID;
	   var name = prnt.find('p.productName').text();
	   var icon = prnt.find('a.image').find('img').attr('src');
	   var mrp = prnt.find('p.productPrice').text();*/
	   $('#addToCartP_'+itemid).remove();
		var cart = $('<p id="addToCartP_'+itemid+'" onclick="removeSingleItemFromCart(\''+itemid+'\',\''+name+'\',\''+icon+'\',\''+mrp+'\')"><button class="btn btn-red small addedBtn">Added</button></p>')
		$('#addToCartDiv_'+itemid).append(cart);
		$('.addedBtn').css('background',colortheme);
	   ongoaddtocart(itemid, name, icon, 1, mrp);
}
function removeSingleItemFromCart(itemid, name, icon, mrp){
	$('#addToCartP_'+itemid).remove();
	var cart = $('<p id="addToCartP_'+itemid+'" onclick="addToCartSingleItem(\''+itemid+'\',\''+name+'\',\''+icon+'\',\''+mrp+'\')"><button class="btn btn-dark small"> Add to Cart</button></p>')
	$('#addToCartDiv_'+itemid).append(cart);
	ongoModifyCartItem(itemid, false);
}

function ongoLoginFB(){
	window.location.href = ongosettings.ongourl + '/FB/facebook?orgId=1&cb='+ongosettings.murl+'/profile/fb.html?1=1';
}
function displayNone(div)
{
	$(div).css('display','none');
}

function ongoBuildCampaigns(type){
	// Build left menu
	ongoBuildMenu();
	
	var cs = jQuery.parseJSON(readCookie('cs'));
	var d = jQuery.parseJSON(readCookie('data'));
	
	var notijt = readCookie('notijt') == null || readCookie('notijt') == '' ? readCookie1('notijt') : readCookie('notijt');
	
	// Screen Name
	$('#screenName').html(notijt);
	
    var pms = {
			type : notijt,
			pageNumber:pageNum,
			pageSize:noOfProducts
			/*, refTypeProperty: 'storeId'
			, refId: cs.id*/
	}
	var jids = readCookie('jids') == '0'  ? '0' : readCookie('jids');
	if(jids != '0' && type !=''){
		/*/Services/getMasters?mallId=3&PrefferedJobs=238_239_240*/	
	   pms.PrefferedJobs=jids;
	   $('#screenName').html('Campaign Items');
   }
	$('#grid').html('')
	var mt = readCookie('mt') == null || readCookie('mt') == '' ? readCookie1('mt') : readCookie('mt');	
	ongoGetMastersAsync(pms, function(cats){
		totalCampaigns=cats.totalNumRecords;
		if(totalCampaigns > 0){
		$.each(cats.jobs, function(idx){
			var job = cats.jobs[idx];
			
			if(jids != '0' && type !=''){
				var li = $('<li class="product" itemid="'+job['id']+'">')
				   .append('<a class="image"><img src="'+job['Image_URL']+'"></a>')
				   .append($('<a class="name"></a>').html(job['Name']))
				  // if(mt != null && mt != '' && mt == 1){
					   // Products
				   
				   var price = job['MRP'];
				   price = price == null || price == '' ? '0': price;
				   var cart = $('<div id="addToCartDiv_'+job['id']+'">'+
						   '<p class="addToCart" id="addToCartP_'+job['id']+'" onclick="addItemToCart(\''+job['id']+'\',\''+job['Name']+'\',\''+job['Image_URL']+'\',\''+price+'\')">'+
						   '<button id="addToCartButton_'+job['id']+'" class="btn btn-dark small full cartBtn"> Add to cart</button></p></div>');
					   
		           if(parseInt(price)>0){
		        	   if(d.currencyType == 'INR'){
			            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-inr"></i>&nbsp;'+price));
			              }
			              else{
			            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-dollar"></i>&nbsp;'+price));
			              }
						  li.append(cart);
		           }
				   
					   /*var price = job['MRP'];
					   
					   price = price == null || price == '' ? '0': price;
					   var cart = $('<p class="addToCart"><button class="btn btn-dark small full"><i class="glyphicon glyphicon-shopping-cart"></i> Add to cart</button></p>');
	                   if(parseInt(price)>0)
	                	   if(d.currencyType == 'INR'){
	  		            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-inr"></i>&nbsp;'+price));
	  		              }
	  		              else{
	  		            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-dollar"></i>&nbsp;'+price));
	  		              }
					   li.append(cart);
					   cart.click(function(event){
						   event.preventDefault();
						   var prnt = $(this).parent()
						   var itemid = prnt.attr('itemid')
						   var name = prnt.find('a.name').text()
						   var icon = prnt.find('a.image').find('img').attr('src')
						   var mrp = prnt.find('a.price').attr('p')
						   ongoaddtocart(itemid, name, icon, 1, mrp)
					   });*/

				   
				  // }
				li.find('a.image').click(function(){
					createCookie('jid', job['id']);
					createCookie('jname', job['Name']);
					window.location.href = relativePathToHome+'offers/singleItem.html';
				});
			}else {
				var li = $('<li class="product" itemid="'+job['id']+'">')
				if(job['Campaign_Images'].length > 50){
					li.append('<a class="image"><img src="'+job['Campaign_Images']+'"></a>')
				}else{
					li.append('<a class="image"><img src="'+job['Campaign_Images'][0]+'"></a>')
				}
				   li.append($('<a class="name"></a>').html(job['Name']))
				   li.append($('<a class="name"></a>').html(job['createdOn']))
				li.find('a.image').click(function(){
					createCookie('jids', job['Campaign_Jobs']);
					window.location.href= 'campaignItems.html';		
				});
			}
			
			$('#grid').append(li)
		});
	}else{
		var msgblock =  $('<div class="blocksPlaceholder">')
		   .append('<div class="block"><p class="heading">There are no campaigns to display.</p></div>');
		$('#grid').append(msgblock)
	}
	});
}

var pageNo=1;
var noOfOffers=20;
var totalOffers;
function ongoBuildOffers(){
	// Build left menu
	ongoBuildMenu();
	
	var cs = jQuery.parseJSON(readCookie('cs'));
	
	var notijt = readCookie('notijt') == null || readCookie('notijt') == '' ? readCookie1('notijt') : readCookie('notijt');
	
	// Screen Name
	$('#screenName').html(notijt);
	
    var pms = {
			type : notijt,
			pageNumber:pageNo,
			pageSize:noOfOffers
			/*, refTypeProperty: 'storeId'
			, refId: cs.id*/
	}
	
	$('#grid').html('')
	
	var mt = readCookie('mt') == null || readCookie('mt') == '' ? readCookie1('mt') : readCookie('mt');	
	ongoGetMastersAsync(pms, function(cats){
		totalProducts=cats.totalNumRecords;
		if(totalProducts > 0){
		$.each(cats.jobs, function(idx){
			var job = cats.jobs[idx];
			
			var li = $('<li class="product" itemid="'+job['id']+'">')
			   .append('<a class="image"><img src="'+job['Image_URL']+'"></a>')
			   .append($('<a class="name"></a>').html(job['Name']))
			   .append($('<a class="name"></a>').html(job['Description']))
			   .append($('<a class="name"></a>').html('Valid till : '+job['Valid till']))
			   /*if(mt != null && mt != '' && mt == 1){
				   // Products
				   var price = job['MRP'];
				   price = price == null || price == '' ? '0': price;
				   var cart = $('<p class="addToCart"><button class="btn btn-dark small full"><i class="glyphicon glyphicon-shopping-cart"></i> Add to cart</button></p>');
                   if(parseInt(price)>0)
				   li.append($('<a class="price" p="'+price+'"></a>').html('Rs. '+price));
				   li.append(cart);
				   cart.click(function(event){
					   event.preventDefault();
					   var prnt = $(this).parent()
					   var itemid = prnt.attr('itemid')
					   var name = prnt.find('a.name').text()
					   var icon = prnt.find('a.image').find('img').attr('src')
					   var mrp = prnt.find('a.price').attr('p')
					   ongoaddtocart(itemid, name, icon, 1, mrp)
				   });

			   
			   }*/
				   
			   
			li.find('a.image').click(function(){
				createCookie('jid', job['id']);
				createCookie('jname', job['Name']);
				window.location.href= 'singleOffer.html';		
			});
			$('#grid').append(li)
		});
	}else{
		var msgblock =  $('<div class="blocksPlaceholder">')
		   .append('<div class="block"><p class="heading">There are no offers to display.</p></div>');
		$('#grid').append(msgblock)
	}
	});
 checkOffersCount();
}
function checkOffersCount()
{
  if(totalOffers>20)
  {
	  totalOffers=totalOffers-20;
  	$('#loadMore').css('display','block');
  	moreProductsFlag=true;
  }
  else
  {	
  		$('#loadMore').css('display','none');
  		moreProductsFlag=false;
  }
}

function ongoBuildGeneralServices(){
	// Build left menu
	ongoBuildMenu();
	
	var cs = jQuery.parseJSON(readCookie('cs'));
	
	var scjt = readCookie('ServicesCategories') == null || readCookie('ServicesCategories') == '' ? readCookie1('ServicesCategories') : readCookie('ServicesCategories');
	
	// Screen Name
	$('#screenName').html('General Services');
	
    var pms = {
			type : scjt
			/*, refTypeProperty: 'storeId'
			, refId: cs.id*/
	}
	
	$('#grid').html('')
	
	ongoGetMastersAsync(pms, function(cats){
		totalProducts=cats.totalNumRecords;
		$.each(cats.jobs, function(idx){
			var job = cats.jobs[idx];
			var IsSpecialService = job['IsSpecialService'];
			//alert(IsSpecialService.toLowerCase())
			if(IsSpecialService.toLowerCase() != '' && (IsSpecialService.toLowerCase() == 'yes' || IsSpecialService.toLowerCase() == 'true') ){
				var li = $('<li class="product" itemid="'+job['id']+'">')
				   .append('<a class="image"><img src="'+job['Image_URL']+'"></a>')
				   .append($('<a class="name"></a>').html(job['Name']))
				   .append($('<a class="name"></a>').html(job['Description']))
				$('#grid').append(li)
			}
			
		});
	});
}

function ongoBuildServices(){
	// Build left menu
	ongoBuildMenu();
	var scjt = readCookie('scjt') == null || readCookie('scjt') == '' ? readCookie1('scjt') : readCookie('scjt');
	var desc = readCookie('desc') == null || readCookie('desc') == '' ? readCookie1('desc') : readCookie('desc');
	var image_URL = readCookie('image_URL') == null || readCookie('image_URL') == '' ? readCookie1('image_URL') : readCookie('image_URL');
	
	// Screen Name
	$('#screenName').html(scjt);
	
	$('#grid').html('')
	
	if(desc == null || desc == '' || desc == 'null'){
		desc = 'Description is not available';
	}
	/*var div = $('<div class="productImageBlock">')
	   .append('<a class="image"><img src="'+image_URL+'"></a>');
	// service info 
	var div1 = ('<div class="blocksPlaceholder">'+
	                '<div class="block">'+
	                  '<p class="heading" id="">&nbsp;'+scjt+'</p>'+
	                  
	                '</div>'+
	             '</div>');
	var div2 = ('<div class="blocksPlaceholder">'+
            '<div class="block">'+
              '<p class="heading" id=""><strong>Description</strong></p>'+
              '<p class="heading" id="">&nbsp;'+desc+'</p>'+
            '</div>'+
         '</div>');
	$('#tabInfo').append(div);
	$('#tabInfo').append(div1);
	$('#tabInfo').append(div2);*/
	
	var div = $('<div class="productImageBlock">')
	   .append('<a class="image"><img src="'+image_URL+'"></a>');
	
	var div1 = ('<div class="blocksPlaceholder">'+
            '<div class="block">'+
              '<p class="heading" id="">&nbsp;'+scjt+'</p>'+
            '</div>'+
         '</div>');
	var div2 = ('<div class="blocksPlaceholder">'+
	    '<div class="block">'+
	      '<p class="heading" id=""><strong>Description</strong></p>'+
	      '<p class="heading" id="">&nbsp;'+desc+'</p>'+
	    '</div>'+
	 '</div>');
	
	
	var mainDiv = $('<div class="productView" id="productView">');
	if(image_URL != null && image_URL != '' && image_URL != 'undefined'){
		mainDiv.append(div);
	}
	mainDiv.append(div1);
	mainDiv.append(div2);
	$('#tabInfo').append(mainDiv);
	
	var scjtNFields = ongoGetCurrentServiceAndFields(scjt);
    if(scjtNFields != null && scjtNFields != ''){
    	var iframeUrl = ongosettings.ongourl+'/Services/showPostAJob?userId='+ongoorgid()+'&weburl='+scjtNFields.subdomain+'&noHeader=true&storeId=0&hideAppTabs=false&useLoginUserEmail=false&jobType='+scjtNFields.id+'&catagoryType=ServicesCategories&hideBar=true';
    	$('iframe#webAppServiceFrom').attr('src', iframeUrl);
    	/*var scjtFields = scjtNFields.fields;
    	var div = $('<div>');
    	div.append('<div><input type="text" id="emaiId" name="email" class="control-mobile" placeholder="Email Id*"></div>')
    	$.each(scjtFields, function(idx){
    		var field = scjtFields[idx];
    		if(field.name != 'storeId')
    		if(field.type == 'Attachment'){
    			div.append('<div><input type="file" id="state" name="'+field.name+'" class="control-mobile"></div>')
    		}else{
    			div.append('<div><input type="text" id="state" name="'+field.name+'" class="control-mobile" placeholder="'+field.name+'"></div>')
    		}
    	});
    	$('#serviceFormFields').append(div);*/
    }
    
    //type=Contact%20Us&mallId=33&consumerId=64
    var  loginid = readCookie('loginid');
    if(loginid != null && loginid != ''){
    	var pms = {
    			type : scjt,
    			consumerId : loginid
    	}
    	ongoGetMastersAsync(pms, function(cats){
    		$('#loginHistory').hide();
    		$.each(cats.jobs, function(idx){
    			var job = cats.jobs[idx];
    			
    			var div3 = ('<div class="blocksPlaceholder">'+
	    		                '<div class="block">'+
	    		                  '<p class="heading" id="">&nbsp;'+job['Name']+'</p>'+
	    		                  '<p class="heading" id="">Staus :&nbsp;'+job['Current_Job_Status']+'</p>'+
	    		                  '<p class="heading" id="">Reviews : '+job['totalReviews']+'</p>'+
	    		                '</div>'+
	    		             '</div>');
    			$('#myServices').append(div3);
    			
    		});
    	});
    }
    
}

function ongoBuildWeblinks(){
	// Build left menu
	ongoBuildMenu();
	var weblinkName = readCookie('weblinkName') == null || readCookie('weblinkName') == '' ? readCookie1('weblinkName') : readCookie('weblinkName');
	var weblinkUrl = readCookie('weblinkUrl') == null || readCookie('weblinkUrl') == '' ? readCookie1('weblinkUrl') : readCookie('weblinkUrl');
	$('#screenName').html(weblinkName)
	
	$('iframe#weblinkIframe').attr('src', weblinkUrl);
	
}

function ongoGetCurrentServiceAndFields(scjt){
	
	//var scjt = readCookie('scjt') == null || readCookie('scjt') == '' ? readCookie1('scjt') : readCookie('scjt');
    var pms = {
			type : 'allServicesJobTypes'
	}
    var csjt = {};
	ongoGetMastersAsync(pms, function(cats){
		var userDetails = cats.userdetails;
		$.each(cats.orgs, function(idx){
			var jt = cats.orgs[idx];
			if(jt.type == scjt){
				var fields = [];
				csjt.id = jt.id;
				csjt.type = jt.type;
				$.each(jt.Fields, function(fidx){
					var field = jt.Fields[fidx];
					var fld ={};
					//alert(field.name)
					fld.name = field.name;
					fld.mandatory = field.mandatory;
					fld.type = field.type;
					fld.addMore = field.addMore;
					fields[fidx] = fld;
				});
				csjt.fields = fields;
			}
		});
		csjt.userId = userDetails.id;
		csjt.subdomain = userDetails.subdomain;
		
	});
	return csjt;
}
function ongoSubmitService(form){
	
	var form = $('#serviceForm1');
	
	var scjt = readCookie('scjt') == null || readCookie('scjt') == '' ? readCookie1('scjt') : readCookie('scjt');
	var scjtNFields = ongoGetCurrentServiceFields(scjt);
	
	var scjtFields = scjtNFields.fields
	/*json{"list":[{"Name":"xssgbg","Contact number":"","Description":"","Email Id":"cxsample@gmail.com","Image":""}]}*/
	var pms = {};
	pms.consumerEmail = form.find('input[name=email]').val();
	pms.type = scjt;
	pms.userId = ongoorgid();
	pms.dt='CAMPAIGNS';
	pms.category='Services';
	var list = []
	var fld ={};
	//var srcFile = []
	$.each(scjtFields, function(idx){
		var field = scjtFields[idx];
		if(field.name != 'storeId'){
			/*if(field.type == 'Attachment'){
				//srcFile[idx] = form.find('input[name="'+field.name+'"]').val();
			}else{*/
				fld[field.name] = form.find('input[name="'+field.name+'"]').val();
			//}
		}
	});
	list[0] = fld;
	var o ={};
	o.list =list;
	pms.json=JSON.stringify(o);
	
	//pms.srcFile = pms;
	ongoAjaxRequestAsync("POST",'/MobileAPIs/postedJobs', pms, function(res){
		if(res.statusCode == '200'){
			ongoAfterLogin(res)
		}else{
			getToast(res.message);
			/*alert(res.message)*/
		}
	}, null, false)
	
	
}
function ongologinfromSearchPage(email){
	var pms = {};
	pms.userEmailId = email
	pms.firstName = ''
	pms.lastName = ''
	pms.orgId = ongoorgid();
	pms.dt='DEVICES';
	pms.isLoginWithFB=true;
	ongoAjaxRequestAsync("POST",'/MobileAPIs/regAndloyaltyAPI', pms, function(conLoginres){
		if(conLoginres.statusCode == '200'){
			createCookie('loginorg', conLoginres.orgId)
			createCookie('loginid', conLoginres.UserId)
			createCookie('loginemail', conLoginres.emailId)
			createCookie('conLoginres', JSON.stringify(conLoginres))
		}
	}, null, false)	;

	
	return false;
}


function ongoBuildCustomFeatured(featuredType, divId){
	var d = jQuery.parseJSON(readCookie('data'));
	var pms = {
			type : featuredType,
			pageNumber:1,
			pageSize:0,
			unlimited:true
	}
	var featuredTabCount =0;
	ongoGetMastersAsync(pms, function(featuredJobs){
		$.each(featuredJobs.jobs, function(idx){
			var job = featuredJobs.jobs[idx];
			pms.PrefferedJobs = job['Campaign_Jobs'];
			//featuredTabCount++;
			ongoGetMastersAsync(pms, function(featuredItems){
				featuredTabCount++;
				$('.featuredBlock').show();
				$('.featuredBlock').append('<div id="wrapper'+idx+'_'+divId+'" class="wrapper"><div id="scroller'+idx+'_'+divId+'" class="scroller"><ul id="scrollerinner'+idx+'_'+divId+'" class="scrollerinner"></ul></div></div>');
				var customGroupWid = $('.featuredBlock');
				var nameblock = $('<div class="NameBlock">');
				nameblock.append($('<p class="customHeading"></p>').html(job['Name']));
				nameblock.append($('<a href="#" class="more"><button class="btn">More</button></a>'));
				
				nameblock.find('button').click(function(){
					createCookie('jids', job['Campaign_Jobs']);
					window.location.href= relativePathToHome+'offers/featuredItems.html';		
				});
				var scroller = $('#scroller'+idx+'_'+divId);
				var scrollerinner = $('#scrollerinner'+idx+'_'+divId);
				//featuredTabCount++;
				$.each(featuredItems.jobs, function(id){
	                var featuredItem = featuredItems.jobs[id];
					var mkeyName = featuredItem['Name'];
					var mvalue = featuredItem['Description'];
					var imgUrl = featuredItem['Image_URL'];
					var price = featuredItem['MRP'];
					var wrapper = $('#wrapper'+idx+'_'+divId);
					wrapper.append(nameblock);
					customGroupWid.append(wrapper);
					var block = $('<div class="block">');
					
					if(imgUrl != null && imgUrl != undefined){
						block.append($('<a class="image"><img src="'+imgUrl+'"></a>'));
					}
					
					block.append($('<p class="heading">').html(mkeyName));
					if(price != null && price != '' && price != undefined){
						if(d != null && d != undefined && d.currencyType != null && d.currencyType != undefined){
							if(d.currencyType == 'INR'){
								block.append($('<p class="details">').html('<i class="fa fa-inr"></i>&nbsp;'+price));
				             }else{
				            	  block.append($('<p class="details">').html('<i class="fa fa-dollar"></i>&nbsp;'+price));
				              }
						}else{
							block.append($('<p class="details">').html('<i class="fa fa-inr"></i>&nbsp;'+price));
						}
						
					}else{
						block.append($('<p class="details">').html(mvalue));
					}
					
					block.append($('<div class="clearfix"></div>'));
					scrollerinner.append($('<li class="customwid'+id+'"></li>').append(block));
					
				}); 
			});
			createCookie('featuredTabCount_'+divId, featuredTabCount);
		});
		
	});
}

function ongoBuildFeatured(type){
	// Build left menu
	ongoBuildMenu();
	
	var cs = jQuery.parseJSON(readCookie('cs'));
	var d = jQuery.parseJSON(readCookie('data'));
	
	var notijt = readCookie('Featured') == null || readCookie('Featured') == '' ? readCookie1('Featured') : readCookie('Featured');
	// Screen Name
	$('#screenName').html(notijt);
    var pms = {
			type : notijt,
			pageNumber:1,
			pageSize:0,
			unlimited:true
			/*, refTypeProperty: 'storeId'
			, refId: cs.id*/
	}
	var jids = readCookie('jids') == '0'  ? '0' : readCookie('jids');
	if(jids != '0' && type !=''){
		/*/Services/getMasters?mallId=3&PrefferedJobs=238_239_240*/	
	   pms.PrefferedJobs=jids;
	   $('#screenName').html('Featured Items');
   }
	$('#grid').html('')
	var mt = readCookie('mt') == null || readCookie('mt') == '' ? readCookie1('mt') : readCookie('mt');	
	ongoGetMastersAsync(pms, function(cats){
		totalCampaigns=cats.totalNumRecords;
		if(totalCampaigns > 0){
		$.each(cats.jobs, function(idx){
			var job = cats.jobs[idx];
			
			if(jids != '0' && type !=''){
				var li = $('<li class="product" itemid="'+job['id']+'">')
				   .append('<a class="image"><img src="'+job['Image_URL']+'"></a>')
				   .append($('<a class="name"></a>').html(job['Name']))
				  // if(mt != null && mt != '' && mt == 1){
					   // Products
				   
				   var price = job['MRP'];
				   price = price == null || price == '' ? '0': price;
				   var cart = $('<div id="addToCartDiv_'+job['id']+'">'+
						   '<p class="addToCart" id="addToCartP_'+job['id']+'" onclick="addItemToCart(\''+job['id']+'\',\''+job['Name']+'\',\''+job['Image_URL']+'\',\''+price+'\')">'+
						   '<button id="addToCartButton_'+job['id']+'" class="btn btn-dark small full cartBtn"> Add to cart</button></p></div>');
					   
		           if(parseInt(price)>0){
		        	   if(d.currencyType == 'INR'){
			            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-inr"></i>&nbsp;'+price));
			              }
			              else{
			            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-dollar"></i>&nbsp;'+price));
			              }
						  li.append(cart);
		           }
				   
					   /*var price = job['MRP'];
					   
					   price = price == null || price == '' ? '0': price;
					   var cart = $('<p class="addToCart"><button class="btn btn-dark small full"><i class="glyphicon glyphicon-shopping-cart"></i> Add to cart</button></p>');
	                   if(parseInt(price)>0)
	                	   if(d.currencyType == 'INR'){
	  		            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-inr"></i>&nbsp;'+price));
	  		              }
	  		              else{
	  		            	 li.append($('<a class="price" p="'+price+'"></a>').html('<i class="fa fa-dollar"></i>&nbsp;'+price));
	  		              }
					   li.append(cart);
					   cart.click(function(event){
						   event.preventDefault();
						   var prnt = $(this).parent()
						   var itemid = prnt.attr('itemid')
						   var name = prnt.find('a.name').text()
						   var icon = prnt.find('a.image').find('img').attr('src')
						   var mrp = prnt.find('a.price').attr('p')
						   ongoaddtocart(itemid, name, icon, 1, mrp)
					   });*/

				   
				  // }
				li.find('a.image').click(function(){
					createCookie('jid', job['id']);
					createCookie('jname', job['Name']);
					window.location.href = relativePathToHome+'offers/singleItem.html';
				});
			}else {
				var li = $('<li class="product" itemid="'+job['id']+'">')
				if(job['Campaign_Images'].length > 50){
					li.append('<a class="image"><img src="'+job['Campaign_Images']+'"></a>')
				}else{
					li.append('<a class="image"><img src="'+job['Campaign_Images'][0]+'"></a>')
				}
				   li.append($('<a class="name"></a>').html(job['Name']))
				   li.append($('<a class="name"></a>').html(job['createdOn']))
				li.find('a.image').click(function(){
					createCookie('jids', job['Campaign_Jobs']);
					window.location.href= 'featuredItems.html';		
				});
			}
			
			$('#grid').append(li)
		});
	}else{
		var msgblock =  $('<div class="blocksPlaceholder">')
		   .append('<div class="block"><p class="heading">There are no '+notijt+' to display.</p></div>');
		$('#grid').append(msgblock)
	}
	});
}


 function getToast(message)
      {
	  $('#modalerrorpopup').modal('show');
	   $('#modalerrormsg').html(message);
      }
 var loadEventSliderFlag=true;
 var event_jssor_slider1;
 function eventDetail(id){
	 $('#eventslider_container').show();
		$('#calendarCarousel' ).show('slide', {direction: 'down'},  'slow');
		if(loadEventSliderFlag)
		{
			loadEventSliderFlag=false;
			setSliderWidth($('#eventslider_container'),$('#slidescoverEvent'));
			   jQuery(document).ready(function ($) {
			            var options = {
			                $DragOrientation: 1,                                //[Optional] Orientation to drag slide, 0 no drag, 1 horizental, 2 vertical, 3 either, default value is 1 (Note that the $DragOrientation should be the same as $PlayOrientation when $DisplayPieces is greater than 1, or parking position is not 0)
			                $BulletNavigatorOptions: {                          //[Optional] Options to specify and enable navigator or not
			                    $Class: $JssorBulletNavigator$,                 //[Required] Class to create navigator instance
			                    $ChanceToShow: 2,                               //[Required] 0 Never, 1 Mouse Over, 2 Always
			                    $ActionMode: 1,                                 //[Optional] 0 None, 1 act by click, 2 act by mouse hover, 3 both, default value is 1
			                    $AutoCenter: 1,                                 //[Optional] Auto center navigator in parent container, 0 None, 1 Horizontal, 2 Vertical, 3 Both, default value is 0
			                    $Steps: 1,                                      //[Optional] Steps to go for each navigation request, default value is 1
			                    $Lanes: 1,                                      //[Optional] Specify lanes to arrange items, default value is 1
			                    $SpacingX: 0,                                   //[Optional] Horizontal space between each item in pixel, default value is 0
			                    $SpacingY: 0,                                   //[Optional] Vertical space between each item in pixel, default value is 0
			                    $Orientation: 1                                 //[Optional] The orientation of the navigator, 1 horizontal, 2 vertical, default value is 1
			                }
			            };
			            event_jssor_slider1 = new $JssorSlider$("eventslider_container", options);

			     });
		} 
		event_jssor_slider1.$PlayTo(id); 
	}
	function isNumber(evt) {
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 43 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}
	function ongoBuildPage(page, rpth, type){
		relativePathToHome = rpth;
		var searchloginemail = readCookie('sremail');
		if(searchloginemail != null && searchloginemail != '' && searchloginemail != 'guest@storeongo.com'){
			 ongologinfromSearchPage(searchloginemail);
		 }
		if(page != 'signin' && page != 'ongoRegWithFBCallback'){
			eraseCookie('loginaddtocart')
		}
		
		if(page == 'index'){
			document.getElementById('landingPage').style.display='none';
			document.getElementById('prepage').style.display='';
			ongoBuildIndex();
		}else if(page == 'customTab'){
			ongoBuildCustomTabs();
		}else if(page == 'products'){
			ongoBuildProducts();
		}else if(page == 'singleItem'){
			ongoBuildSingleItem();
		}else if(page == 'gallery'){
			ongoBuildGallery(false)
		}else if (page == 'galleryindex'){
			ongoBuildGallery(true)
		} else if (page == 'custom'){
			ongoBuildCustom()
		} else if (page == 'signin'){
			ongoSetLogo()
		} else if (page == 'cart'){
			ongoBuildCart()
		} else if(page == 'ongoRegWithFBCallback'){
			ongoRegWithFBCallback()
		}else if (page == 'profile'){
			ongoSetProfile()
		}else if (page == 'updateprofile'){
			ongoUpdateProfile()
		}else if (page == 'updatepassword'){
			updatepassword()
		}else if (page == 'forgotpassword'){
			forgotpassword()
		}else if (page == 'campaigns'){
			ongoBuildCampaigns(type);
		}else if (page == 'offers'){
			ongoBuildOffers();
		}else if (page == 'generalServices'){
			ongoBuildGeneralServices();
		}else if (page == 'services'){
			ongoBuildServices();
		}else if (page == 'weblink'){
			ongoBuildWeblinks();
		}
		else if (page == 'featured'){
			ongoBuildFeatured(type);
		}
		ongoSetCartSize()
	}