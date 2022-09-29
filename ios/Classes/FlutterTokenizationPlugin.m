#import "FlutterTokenizationPlugin.h"
#if __has_include(<flutter_tokenization_plugin/flutter_tokenization_plugin-Swift.h>)
#import <flutter_tokenization_plugin/flutter_tokenization_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_tokenization_plugin-Swift.h"
#endif

@implementation FlutterTokenizationPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterTokenizationPlugin registerWithRegistrar:registrar];
}
@end
