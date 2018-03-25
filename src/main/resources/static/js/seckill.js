var seckill = {
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (goodsId) {
            return '/seckill/' + goodsId + '/exposer';
        },
        execution: function (goodsId) {
            return '/seckill/' + goodsId + '/execution';
        }
    },
    detail: {
        /**
         * 详情页初始化
         * @param params
         */
        init: function (params) {
            var killPhone = $.cookie('userPhone');
            var goodsId = params['goodsId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            if (!seckill.validatePhone(killPhone)) {
                //未填写手机号
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,
                    backdrop: false,
                    keyboard: false
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        $.cookie('userPhone', inputPhone, {
                            expires: 7,
                            path: '/seckill'
                        });
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号码错误</label>').show(400);
                    }
                });
            }
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    seckill.countdown(goodsId, nowTime, startTime, endTime);
                }
            });
        }
    },
    countdown: function (goodsId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                seckill.handleSeckill(goodsId, seckillBox);
            });
        } else {
            seckill.handleSeckill(goodsId, seckillBox);
        }
    },
    validatePhone: function (phone) {
        return phone && phone.length === 11 && !isNaN(phone);
    },
    handleSeckill: function (goodsId, seckillBox) {
        seckillBox.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>').show();
        $.post(seckill.URL.exposer(goodsId), {}, function (result) {
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(goodsId, md5);
                    $('#killBtn').one('click', function () {
                        $(this).addClass('disabled');
                        $.post(killUrl, {
                            md5:md5
                        }, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                seckillBox.html('<span class="label label-success">' + killResult['stateInfo'] + '</span>');
                            }else {
                                seckillBox.html('<span class="label label-success">' + result['error'] + '</span>');
                            }
                        });
                    });
                } else {
                    var node = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(goodsId, start, end);
                }
            } else {

            }

        });

    }
};