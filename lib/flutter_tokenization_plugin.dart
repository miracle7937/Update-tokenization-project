import 'dart:async';

import 'package:flutter/services.dart';

class FlutterTokenizationPlugin {
  static const MethodChannel _channel =
      MethodChannel('flutter_tokenization_plugin');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static openTokenization(String account) async {
    await _channel.invokeMethod('openTokenization', {"account": account});
  }
}
